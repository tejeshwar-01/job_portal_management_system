package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    public EmployerController(JobRepository jobRepository, ApplicationRepository applicationRepository, UserService userService) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User employer = userService.findByEmail(principal.getName()).orElse(null);
        if (employer != null) {
            List<Job> myJobs = jobRepository.findByEmployer(employer);
            List<Application> allApplications = applicationRepository.findByJobIn(myJobs);

            long pendingCount = allApplications.stream().filter(a -> a.getStatus() == ApplicationStatus.PENDING).count();
            long acceptedCount = allApplications.stream().filter(a -> a.getStatus() == ApplicationStatus.ACCEPTED).count();

            model.addAttribute("jobs", myJobs);
            model.addAttribute("applications", allApplications);
            model.addAttribute("pendingCount", pendingCount);
            model.addAttribute("acceptedCount", acceptedCount);
        }
        return "employer/dashboard";
    }

    @GetMapping("/post-job")
    public String postJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "employer/post-job";
    }

    @PostMapping("/post-job")
    public String postJobSubmit(@ModelAttribute Job job, Principal principal) {
        User employer = userService.findByEmail(principal.getName()).orElseThrow();
        job.setEmployer(employer);
        jobRepository.save(job);
        return "redirect:/employer/dashboard?success";
    }

    @GetMapping("/applications/{id}/status")
    public String updateApplicationStatus(@PathVariable Long id, @RequestParam ApplicationStatus status) {
        Application app = applicationRepository.findById(id).orElseThrow();
        app.setStatus(status);
        applicationRepository.save(app);
        return "redirect:/employer/dashboard";
    }
}
