package payroll.utils;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import payroll.controller.KnowledgeController;
import payroll.entity.Knowledge;

@Component
public class KnowledgeResourceAssembler implements ResourceAssembler<Knowledge, Resource<Knowledge>> {
	
	@Override
	public Resource<Knowledge> toResource(Knowledge knowledge) {
		Resource<Knowledge> resourceKnowledge = new Resource<>(knowledge,
				linkTo(methodOn(KnowledgeController.class).findAllByEmployeeId(knowledge.getEmployee().getId())).withSelfRel()
			);

		return resourceKnowledge;
	}
}
