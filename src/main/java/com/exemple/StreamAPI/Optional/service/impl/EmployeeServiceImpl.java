package com.exemple.StreamAPI.Optional.service.impl;

import com.exemple.StreamAPI.Optional.exception.EmployeeAlreadyAddedException;
import com.exemple.StreamAPI.Optional.exception.EmployeeNotFoundException;
import com.exemple.StreamAPI.Optional.exception.EmployeeStorageIsFullException;
import com.exemple.StreamAPI.Optional.model.Employee;
import com.exemple.StreamAPI.Optional.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.apache.tomcat.util.http.parser.HttpParser.isAlpha;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Map<String, Employee> employeeMap;

    public EmployeeServiceImpl() {
        this.employeeMap = new HashMap<>();
    }

    @Override
    public Employee addEmployee(String firstName, String lastName, int salary, int department) {

        validateInput(firstName, lastName);

        if (employeeMap.containsKey(firstName + lastName)) {
            throw new EmployeeAlreadyAddedException("Такой сотрудник уже существует");
        }
        employeeMap.put((firstName + lastName),
                new Employee(firstName, lastName, salary, department));
        return employeeMap.get(firstName + lastName);
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {

        validateInput(firstName, lastName);

        if (employeeMap.containsKey(firstName + lastName)) {
            return employeeMap.get(firstName + lastName);
        }
        throw new EmployeeNotFoundException("Сотрудник не найден");
    }

    @Override
    public Collection<Employee> findAllEmployees() {
        return Collections.unmodifiableCollection(employeeMap.values());
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {

        validateInput(firstName, lastName);

        employeeMap.remove(firstName + lastName);
        return new Employee(firstName, lastName);
    }

    private void validateInput(String firsName, String lastName) {
        if (!(isAlpha(Integer.parseInt(firsName)) && isAlpha(Integer.parseInt(lastName)))) {
            throw new EmployeeStorageIsFullException("incorrect name or surname");
        }
    }
}
