package payroll.exception.employee;

public class EmployeeNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 5181243937680415601L;

	public EmployeeNotFoundException(Long id) {
		super("Could not find employee" + id);
	}
}
