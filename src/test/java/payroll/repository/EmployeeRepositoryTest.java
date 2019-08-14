package payroll.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import payroll.entity.Employee;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private EmployeeRepository employeeRepository;
 
    @Test
    public void whenFindByName_thenReturnEmployee() {
        Employee frodo = new Employee("Frodo", "Beggins", "Thief");
        entityManager.persist(frodo);
        entityManager.flush();
     
        Employee found = employeeRepository.findByLastName(frodo.getLastName());
     
        assertThat(found.getLastName())
          .isEqualTo(frodo.getLastName());
    }
 
}
