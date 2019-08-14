package payroll.utils;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import payroll.controller.EmployeeController;
import payroll.controller.KnowledgeController;
import payroll.entity.Employee;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class EmployeeResourceAssembler implements ResourceAssembler<Employee, Resource<Employee>> {
	
	@Override
	public Resource<Employee> toResource(Employee employee) {
		return new Resource<>(employee,
				linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
				linkTo(methodOn(KnowledgeController.class).findAllByEmployeeId(employee.getId())).withRel("knowledges"),
				linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
			);
	}
}
