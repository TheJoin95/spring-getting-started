package payroll.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Skill {
	
	private @Id @GeneratedValue Long id;
	private String name;
	private String description;
	private boolean enabled;

	public Skill() {}
	
	public Skill(String name, String description) {
		this.name = name;
		this.description = description;
		this.enabled = true;
	}

}
