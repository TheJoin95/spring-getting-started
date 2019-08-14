package payroll.utils;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import payroll.controller.OrderController;
import payroll.entity.Order;
import payroll.entity.Status;

@Component
public class OrderResourceAssembler implements ResourceAssembler<Order, Resource<Order>> {
	
	@Override
	public Resource<Order> toResource(Order order) {
		Resource<Order> resourceOrder = new Resource<>(order,
				linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
				linkTo(methodOn(OrderController.class).all()).withRel("orders")
			);
		
		if(order.getStatus() == Status.IN_PROGRESS) {
			resourceOrder.add(
					linkTo(methodOn(OrderController.class).cancel(order.getId()))
					.withRel("cancel"));
			resourceOrder.add(
					linkTo(methodOn(OrderController.class).complete(order.getId()))
					.withRel("complete"));
		}
		
		return resourceOrder;
	}
}
