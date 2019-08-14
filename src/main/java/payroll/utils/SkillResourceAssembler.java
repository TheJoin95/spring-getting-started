package payroll.utils;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import payroll.controller.EmployeeController;
import payroll.controller.KnowledgeController;
import payroll.controller.SkillController;
import payroll.entity.Skill;

@Component
public class SkillResourceAssembler implements ResourceAssembler<Skill, Resource<Skill>> {
	
	@Override
	public Resource<Skill> toResource(Skill skill) {
		return new Resource<>(skill,
				linkTo(methodOn(SkillController.class).one(skill.getId())).withSelfRel(),
				linkTo(methodOn(KnowledgeController.class).findAllBySkillId(skill.getId())).withRel("knowledges"),
				linkTo(methodOn(SkillController.class).all()).withRel("skill")
			);
	}
}
