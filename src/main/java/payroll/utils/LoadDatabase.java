package payroll.utils;

import lombok.extern.slf4j.Slf4j;
import payroll.entity.Employee;
import payroll.entity.Knowledge;
import payroll.entity.Level;
import payroll.entity.Order;
import payroll.entity.Skill;
import payroll.entity.Status;
import payroll.repository.EmployeeRepository;
import payroll.repository.KnowledgeRepository;
import payroll.repository.OrderRepository;
import payroll.repository.SkillRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository, OrderRepository orderRepository, KnowledgeRepository knowledgeRepository, SkillRepository skillRepository) {
		/*orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
		orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

		orderRepository.findAll().forEach(order -> {
		  log.info("Preloaded " + order);
		});
		
		repository.save(new Employee("Bilbo", "Baggins", "burglar"));
		repository.save(new Employee("Frodo", "Baggins", "thief"));
		
		skillRepository.save(new Skill("PHP", "PHP language"));
		skillRepository.save(new Skill("Java", "Java language"));
		skillRepository.save(new Skill("Spring Framework", "Spring"));
		
		repository.findAll().forEach(employee -> {
			skillRepository.findAll().forEach(skill -> {
				Knowledge k = new Knowledge(skill, Level.VERY_HIGH, employee);
				log.info("Loading: " + knowledgeRepository.save(k));
			});
		});*/
		
		
		return args -> {
			log.info("Loaded all");
		};
	}
	
}
