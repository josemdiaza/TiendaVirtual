package base.model.employee.persistance;

import base.model.employee.domain.Employee;
import base.model.employee.exception.EmployeeException;
import base.model.employee.exception.ErrorCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class EmployeeDAOImp implements EmployeeDAO {

    private List<Employee> employees;
    private static final String fileName;

    public EmployeeDAOImp() {
        this.employees = new ArrayList<>();
        readEmployees();
    }

    static {
        fileName = "employees.txt";
    }

    @Override
    public List<Employee> readEmployees() {
        try (var reader = Files.newBufferedReader(Paths.get(fileName))) {
            saveEmployeesOnList(reader);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file " + fileName);
        }
        return employees;
    }

    private void saveEmployeesOnList(BufferedReader reader) throws IOException, InputMismatchException {
        while (reader.readLine() != null) {
            reader.readLine();
            int code = Integer.parseInt(reader.readLine().trim());
            reader.readLine();
            String firstName = reader.readLine().trim();
            reader.readLine();
            String lastName = reader.readLine().trim();
            reader.readLine();
            String password = reader.readLine().trim();
            employees.add(new Employee(code, firstName, lastName, password));
        }
    }

    @Override
    public Employee getEmployeeByCode(int code) {
        for (Employee employee : employees) {
            if (employee.getCode() == code) {
                return employee;
            }
        }
        throw new EmployeeException("Error - code not found", ErrorCode.ERROR_INVALID_CODE);
    }

    @Override
    public boolean updateEmployees() {
        return updateEmployees(this.employees);
    }

    @Override
    public boolean updateEmployees(List<Employee> employees) {
        try (var writer = Files.newBufferedWriter(Paths.get(fileName))) {
            saveEmployeesOnFile(writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void saveEmployeesOnFile(BufferedWriter writer) throws IOException {
        for (Employee employee : employees) {
            writer.write(String.format("%s%n%s%n%d%n%s%n%s%n%s%n%s%n%s%n%s%n",
                    "[empleado]",
                    "[code]",
                    employee.getCode(),
                    "[nombre]",
                    employee.getFirstName(),
                    "[apellidos]",
                    employee.getLastName(),
                    "[contraseña]",
                    employee.getPassword()));
        }
    }
}
