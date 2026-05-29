package com.jobportal.controller;

import com.jobportal.repository.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final JobRepository jobRepository;

    public HomeController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("jobs", jobRepository.findByCategoryContainingIgnoreCaseOrTitleContainingIgnoreCase(search, search));
        } else {
            model.addAttribute("jobs", jobRepository.findAll());
        }
        return "index";
    }
}
