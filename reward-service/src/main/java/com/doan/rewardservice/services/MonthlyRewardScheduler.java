package com.doan.rewardservice.services;
import com.doan.rewardservice.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Random;

@Component
public class MonthlyRewardScheduler {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private RestTemplate restTemplate;

    private final Random random = new Random();

    // Method to assign random points to all employees, scheduled to run once a month
    @Scheduled(cron = "0 0 0 1 * ?") // At 00:00:00 on the 1st day of every month
    public void assignMonthlyPoints() {
        // Fetch the list of all employees from EmployeeService
        // Adjust the URL
        String employeeServiceUrl = "http://localhost:8081/api/employees";
        EmployeeDTO[] employees = restTemplate.getForObject(employeeServiceUrl, EmployeeDTO[].class);

        if (employees != null) {
            for (EmployeeDTO employee : employees) {
                int points = random.nextInt(50) + 1; // Generate random points between 1 and 50
                rewardService.assignMonthlyPoints((long) employee.getEmployeeId(), points);
                System.out.println("Assigned " + points + " points to employee ID: " + employee.getEmployeeId());
            }
        } else {
            System.out.println("Failed to retrieve employees from EmployeeService.");
        }
    }
}