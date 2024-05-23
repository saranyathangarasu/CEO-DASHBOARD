package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import application.service.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/totalStudents")
    public ResponseEntity<Integer> getTotalStudents() {
        int totalStudents = userService.getTotalStudents();
        return ResponseEntity.ok(totalStudents);
    }

    @GetMapping("/totalWorkers/{department}")
    public ResponseEntity<Integer> getTotalWorkersByDepartment(@PathVariable String department) {
        int totalWorkers = userService.getTotalWorkersByDepartment(department);
        return ResponseEntity.ok(totalWorkers);
    }

}

