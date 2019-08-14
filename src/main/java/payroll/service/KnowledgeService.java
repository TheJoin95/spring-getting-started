package payroll.service;

import java.util.Collection;

import payroll.repository.KnowledgeRepository;

public interface KnowledgeService extends KnowledgeRepository{
	public <T> Collection<T> findAllByEmployeeId(Long employeeId, Class<T> type);
}
