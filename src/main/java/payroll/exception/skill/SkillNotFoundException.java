package payroll.exception.skill;

public class SkillNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 8859051721883380670L;

	public SkillNotFoundException(Long id) {
		super("Could not find skill " + id);
	}

}
