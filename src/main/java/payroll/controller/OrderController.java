package payroll.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import payroll.entity.Order;
import payroll.entity.Status;
import payroll.exception.order.OrderNotFoundException;
import payroll.repository.OrderRepository;
import payroll.utils.OrderResourceAssembler;

@RestController
public class OrderController {
	
	private final OrderRepository orderRepository;
	private final OrderResourceAssembler assembler;
	
	OrderController(OrderRepository orderRepository, OrderResourceAssembler assembler) {
		this.orderRepository = orderRepository;
		this.assembler = assembler;
	}
	
	@GetMapping("/orders")
	public Resources<Resource<Order>> all() {
		List<Resource<Order>> orders = orderRepository.findAll().stream()
				.map(assembler::toResource)
				.collect(Collectors.toList());
		
		return new Resources<>(orders,
				linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}
	
	@GetMapping("/orders/{id}")
	public Resource<Order> one(@PathVariable Long id) {
		return assembler.toResource(
				orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException(id)));
	}
	
	@PostMapping("/orders")
	public ResponseEntity<Resource<Order>> newOrder(@RequestBody Order order) {
		order.setStatus(Status.IN_PROGRESS);
		Order newOrder = orderRepository.save(order);
		
		return ResponseEntity
				.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
				.body(assembler.toResource(newOrder));
	}
	
	@DeleteMapping("/orders/{id}/cancel")
	public ResponseEntity<ResourceSupport> cancel(@PathVariable Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException(id));
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + order.getStatus() + " status"));
	}
	
	@PutMapping("/orders/{id}/complete")
	public ResponseEntity<ResourceSupport> complete(@PathVariable Long id) {
		Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		
		if(order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(assembler.toResource(orderRepository.save(order)));
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new VndErrors.VndError("Method not allowed", "You can't complete an order with a " + order.getStatus() + " status"));
	}
}
