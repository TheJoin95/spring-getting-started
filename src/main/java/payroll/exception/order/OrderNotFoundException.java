package payroll.exception.order;

public class OrderNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 8859051721883380670L;

	public OrderNotFoundException(Long id) {
		super("Could not find order " + id);
	}
}
