package com.employeemicroservice.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Performed Some Changes in Controller Class
@RestController
public class EmployeeController
{
    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/getData/{name}")
    public String getData(@PathVariable String name)
    {
        return "Hi team, " + "Its " + name;
    }

    // Create API

    @PostMapping("/createEmployee")
    public Employee createEmployee(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee)
    {
        Optional<Employee> oldEmployee = employeeRepository.findById(employee.getEmployeeId());

        if(oldEmployee.isPresent())
        {
            return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/searchEmployee/{employeeId}")
    public Employee searchEmployee(@PathVariable Integer employeeId)
    {
       return CacheOperations.cache.get(employeeId);
    }

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees()
    {
       return new ArrayList<>(CacheOperations.cache.values());
    }

    @DeleteMapping("deleteEmployee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer employeeId)
    {
       if(employeeRepository.findById(employeeId).isPresent())
        {
            employeeRepository.deleteById(employeeId);
            return new ResponseEntity<>("Employee Deleted Successfully...!",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Employee Not Found",HttpStatus.NOT_FOUND);
    }
}
