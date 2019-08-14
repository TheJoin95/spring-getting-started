package payroll.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import payroll.entity.Employee;
import payroll.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@MockBeans({@MockBean(EmployeeRepository.class), @MockBean(EmployeeController.class)})
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository repo;
    
    private String mockLastName = "Beggins";

    @Test
    public void createEmployee_thenReturnJsonArray()
      throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
    	Employee frodo = new Employee("Frodo", this.mockLastName, "Thief");
        String json = objectMapper.writeValueAsString(frodo);
    	
    	 mockMvc.perform(post("/employees")
    	          .contentType(MediaType.APPLICATION_JSON)
    	          .content(json).characterEncoding("utf-8"))
    	          .andExpect(status().isCreated())
    	          .andExpect(jsonPath("$['lastName']", is(this.mockLastName)));
    }
 
    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
      throws Exception {
    	 mockMvc.perform(get("/employees")
    	          .contentType(MediaType.APPLICATION_JSON))
    	          .andExpect(status().isOk())
    	          .andExpect(jsonPath("$['_embedded']['employeeList'][0]['lastName']", is(this.mockLastName)));
    }
}