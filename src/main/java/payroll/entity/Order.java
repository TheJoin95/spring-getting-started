package payroll.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CUSTOMER_ORDER")
public class Order {
	private @Id @GeneratedValue Long id;
	
	private String description;
	private payroll.entity.Status status;
	
	public Order() {}
	
	public Order(String description, payroll.entity.Status status) {
		this.description = description;
		this.status = status;
	}

	public void setStatus(payroll.entity.Status status) {
		this.status = status;
	}
}
