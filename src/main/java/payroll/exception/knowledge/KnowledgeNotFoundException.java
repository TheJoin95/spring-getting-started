package payroll.exception.knowledge;

public class KnowledgeNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 8859051721883380650L;

	public KnowledgeNotFoundException(Long id) {
		super("Could not find knowledge" + id);
	}
}
