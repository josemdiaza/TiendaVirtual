package base.controller.employee;

import base.model.employee.domain.Employee;
import base.model.employee.exception.EmployeeException;
import base.model.employee.exception.ErrorCode;
import base.model.employee.persistance.EmployeeDAOImp;


public class EmployeeController {

    private EmployeeDAOImp employeeDAOImp;

    public EmployeeController() {
        this.employeeDAOImp = new EmployeeDAOImp();
    }

    public Employee getEmployeeByCode(int code) {
        return employeeDAOImp.getEmployeeByCode(code);
    }

    public Employee authenticateEmployee(int code, String password) {
        Employee employee = getEmployeeByCode(code);
        boolean authenticated = password.equals(employee.getPassword());
        if (authenticated) {
            return employee;
        } else {
           throw new EmployeeException("Error - Invalid Password",ErrorCode.ERROR_INVALID_PASSWORD);
        }
    }

    public boolean updateEmployees(){
        return employeeDAOImp.updateEmployees();
    }

}
