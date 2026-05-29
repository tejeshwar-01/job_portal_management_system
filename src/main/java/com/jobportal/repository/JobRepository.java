package com.jobportal.repository;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCategoryContainingIgnoreCaseOrTitleContainingIgnoreCase(String category, String title);
    List<Job> findByEmployer(User employer);
}
