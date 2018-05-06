package warehouse.service;

import warehouse.domain.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(String employeeId);

    List<Employee> getEmployeesByName(String name);

    List<Employee> getEmployeesBySurname(String surname);

    void deleteAllEmployees();

    void addEmployee(Employee employee);
}
