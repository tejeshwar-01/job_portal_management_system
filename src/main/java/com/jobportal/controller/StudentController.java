package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.service.FileStorageService;
import com.jobportal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    public StudentController(JobRepository jobRepository, ApplicationRepository applicationRepository, UserService userService, FileStorageService fileStorageService) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User student = userService.findByEmail(principal.getName()).orElse(null);
        if (student != null) {
            List<Application> myApplications = applicationRepository.findByApplicant(student);
            model.addAttribute("applications", myApplications);
            model.addAttribute("user", student);
        }
        return "student/dashboard";
    }

    @Transactional
    @PostMapping("/apply/{jobId}")
    public String applyJob(@PathVariable Long jobId, @RequestParam("file") MultipartFile file, Principal principal) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            return "redirect:/?error=empty_file";
        }

        User student = userService.findByEmail(principal.getName()).orElseThrow();
        Job job = jobRepository.findById(jobId).orElseThrow();

        if (applicationRepository.existsByApplicantAndJob(student, job)) {
            return "redirect:/?error=already_applied";
        }

        String fileName = fileStorageService.storeFile(file);
        
        String originalName = file.getOriginalFilename();
        if (originalName != null && originalName.length() > 250) {
            originalName = originalName.substring(0, 250);
        }

        Application app = new Application();
        app.setApplicant(student);
        app.setJob(job);
        app.setResumeFileName(originalName);
        app.setResumeFilePath(fileName);

        applicationRepository.save(app);

        return "redirect:/student/dashboard?applied";
    }
}
