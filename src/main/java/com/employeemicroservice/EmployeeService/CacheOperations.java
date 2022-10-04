package com.employeemicroservice.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CacheOperations
{
    @Autowired
    private EmployeeRepository employeeRepository;

    public static HashMap<Integer,Employee> cache = new HashMap<>();
    public List<Employee> employeeList;

    @Scheduled(cron = "0 */2 * ? * *")
    public void loadCache()
    {
        System.out.println("Cache Load Started...");
        employeeList = employeeRepository.findAll();
        if(!employeeList.isEmpty())
        {
            employeeList.forEach(employee -> cache.put(employee.getEmployeeId() , employee));
        }
        System.out.println("Cache Load Ended...");
    }
}
