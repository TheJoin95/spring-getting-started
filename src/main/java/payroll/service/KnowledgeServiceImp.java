package payroll.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import payroll.repository.KnowledgeRepository;

@Service
public abstract class KnowledgeServiceImp implements KnowledgeService {
	@Autowired
	KnowledgeRepository knowledgeRepository;
	
	public <T> Collection<T> findAllByEmployeeId(Long employeeId, Class<T> Type) {
		return knowledgeRepository.findByEmployeeId(employeeId, Type);
	}
}
