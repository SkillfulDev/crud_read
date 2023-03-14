package ua.chernonog.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.chernonog.springcourse.dao.PersonDAO;

@Controller
@RequestMapping("/test-batch")
public class BatchController {

    private final PersonDAO personDAO;

    @Autowired
    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String testMethod() {
        return "/batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch() {
        personDAO.requestWithBatch();
        return "redirect:/people";
    }
@GetMapping("/with")
    public String withBatch() {
        personDAO.requestWitoutBatch();
        return "redirect:/people";
    }

}
