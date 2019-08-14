package payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import payroll.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long>{

}
