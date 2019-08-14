package payroll.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import payroll.entity.Employee;
import payroll.entity.Knowledge;
import payroll.entity.interfaces.BasicKnowledge;
import payroll.exception.employee.EmployeeNotFoundException;
import payroll.exception.knowledge.KnowledgeNotFoundException;
import payroll.exception.skill.SkillNotFoundException;
import payroll.repository.EmployeeRepository;
import payroll.repository.KnowledgeRepository;
import payroll.repository.SkillRepository;
import payroll.utils.KnowledgeResourceAssembler;

@RestController
public class KnowledgeController {

	private final KnowledgeRepository knowledgeRepository;
	private final EmployeeRepository employeeRepository;
	private final SkillRepository skillRepository;
	private final KnowledgeResourceAssembler assembler;
	
	public KnowledgeController(KnowledgeRepository knowledgeRepository, EmployeeRepository employeeRepository, SkillRepository skillRepository, KnowledgeResourceAssembler assembler) {
		this.knowledgeRepository = knowledgeRepository;
		this.assembler = assembler;
		this.employeeRepository = employeeRepository;
		this.skillRepository = skillRepository;
	}
	
	@GetMapping("/knowledges")
	public Resources<Resource<Knowledge>> all() {

	  List<Resource<Knowledge>> knowledges = knowledgeRepository.findAll().stream()
	    .map(assembler::toResource)
	    .collect(Collectors.toList());

	  return new Resources<>(knowledges,
	    linkTo(methodOn(KnowledgeController.class).all()).withSelfRel());
	}

	@GetMapping("/knowledges/{id}")
	public Resource<Knowledge> one(Long id) {
		Knowledge knowledge = knowledgeRepository.findById(id)
				.orElseThrow(() -> new KnowledgeNotFoundException(id));

		return assembler.toResource(knowledge);
	}
	
	@GetMapping("/knowledges/employee-{employeeId}")
	public Resources<Resource<BasicKnowledge>> findAllByEmployeeId(@PathVariable Long employeeId) {
		employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		
		List<Resource<BasicKnowledge>> knowledges = knowledgeRepository.findByEmployeeId(employeeId, BasicKnowledge.class).stream()
				.map((knowledge) -> {
					return new Resource<>(knowledge);
				})
				.collect(Collectors.toList());
		
		return new Resources<>(knowledges,
				linkTo(methodOn(KnowledgeController.class).findAllByEmployeeId(employeeId)).withSelfRel());
	}
	
	@GetMapping("/knowledges/skill-{skillId}")
	public Resources<Resource<Knowledge>> findAllBySkillId(@PathVariable Long skillId) {
		skillRepository.findById(skillId)
				    .orElseThrow(() -> new SkillNotFoundException(skillId));
		
		List<Resource<Knowledge>> knowledges = knowledgeRepository.findBySkillId(skillId, Knowledge.class).stream()
				.map((knowledge) -> {
					return new Resource<>(knowledge);
				})
				.collect(Collectors.toList());
	
		return new Resources<>(knowledges);
	}
	
	@PostMapping("/knowledges/employee-{employeeId}")
	public ResponseEntity<?> newKnowledge(@PathVariable Long employeeId, @RequestBody Knowledge newKnowledge) throws URISyntaxException {
		Employee employee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		
		Long skillId = newKnowledge.getSkill().getId();
		skillRepository.findById(skillId)
		    .orElseThrow(() -> new SkillNotFoundException(skillId));
		
		Collection<Knowledge> knowledge = knowledgeRepository.findByEmployeeAndSkill(employeeId, skillId, Knowledge.class);
		
		newKnowledge.setEmployee(employee);

		if(knowledge.size() == 0) {
			return ResponseEntity.ok(assembler.toResource(knowledgeRepository.save(newKnowledge)));
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new VndErrors.VndError("Method not allowed", "The knowledge for the skill " + newKnowledge.getSkill().getName() + " is already inserted for the employee " + newKnowledge.getEmployee().getName()));
	}
}
