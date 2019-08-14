package payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import payroll.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	public Employee findByLastName(String lastName);
}
