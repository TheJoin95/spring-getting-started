package payroll.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import payroll.entity.Knowledge;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>{

	  @Query("select k from Knowledge k where k.employee.id = ?1")
	  <T> Collection<T> findByEmployeeId(Long id, Class<T> type);

	  @Query("select k from Knowledge k where k.skill.id = ?1")
	  <T> Collection<T> findBySkillId(Long skillId, Class<T> type);

	  @Query("select k from Knowledge k where k.employee.id = ?1 and k.skill.id = ?2")
	  <T> Collection<T> findByEmployeeAndSkill(Long employeeId, Long skillId, Class<T> type);
}
