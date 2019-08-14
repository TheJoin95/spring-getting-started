package payroll.entity.interfaces;

import payroll.entity.Level;
import payroll.entity.Skill;

public interface BasicKnowledge {
	Long getId();
	Skill getSkill();
	Level getLevel();
}
