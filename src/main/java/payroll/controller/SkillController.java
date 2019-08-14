package payroll.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import payroll.entity.Skill;
import payroll.exception.skill.SkillNotFoundException;
import payroll.repository.SkillRepository;
import payroll.utils.SkillResourceAssembler;

@RestController
public class SkillController {
	private final SkillRepository repository;
	private final SkillResourceAssembler assembler;
	
	public SkillController(SkillRepository repository, SkillResourceAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}
	
	@GetMapping("/skills")
	public Resources<Resource<Skill>> all() {

	  List<Resource<Skill>> Skills = repository.findAll().stream()
	    .map(assembler::toResource)
	    .collect(Collectors.toList());

	  return new Resources<>(Skills,
	    linkTo(methodOn(SkillController.class).all()).withSelfRel());
	}
	
	@PostMapping("/skills")
	public ResponseEntity<?> newSkill(@RequestBody Skill newSkill) throws URISyntaxException {	
		Skill skill = new Skill();
		skill.setName(newSkill.getName());
		Example<Skill> skillExample = Example.of(skill);
		Collection<Skill> skillDB = repository.findAll(skillExample);

		if(skillDB.size() == 0) {
			Resource<Skill> resource = assembler.toResource(repository.save(newSkill));
			return ResponseEntity
					.created(new URI(resource.getId().expand().getHref()))
					.body(resource);
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new VndErrors.VndError("Method not allowed", "The skill " + newSkill.getName() + " is already inserted"));
	}
	
	@GetMapping("/skills/{id}")
	public Resource<Skill> one(@PathVariable Long id) {

	  Skill Skill = repository.findById(id)
	    .orElseThrow(() -> new SkillNotFoundException(id));

	  return assembler.toResource(Skill);
	}
	
	@PutMapping("/skills/{id}")
	public ResponseEntity<?> replaceSkill(@RequestBody Skill newSkill, @PathVariable Long id) throws URISyntaxException {
		Skill updatedSkill = repository.findById(id)
				.map(Skill -> {
					Skill.setName(newSkill.getName());
					return repository.save(Skill);
				})
				.orElseGet(() -> {
					newSkill.setId(id);
					return repository.save(newSkill);
				});
		
		Resource<Skill> resource = assembler.toResource(updatedSkill);
		
		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}
	
	@DeleteMapping("/skills/{id}")
	public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
