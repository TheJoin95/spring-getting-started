package payroll.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
@Table(name = "KNOWLEDGE")
public class Knowledge {
	private @GeneratedValue @Id Long id;
	
	@ManyToOne
	@JoinColumn(name = "skill_id")
	@NotNull
	private Skill skill;
	
	private Level level;

	@JsonBackReference
	@ManyToOne
	@NotNull
	private Employee employee;
	
	Knowledge(){}
	
	public Knowledge(Skill skill, Level level, Employee employee) {
		this.skill = skill;
		this.level = level;
		this.employee = employee;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
}
