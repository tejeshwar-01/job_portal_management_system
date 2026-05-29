package com.jobportal.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "The uploaded file is too large! Please upload a file smaller than 50MB.");
        return "redirect:/"; // Redirects to home page on error
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "System Error: " + e.getMessage() + " (Cause: " + (e.getCause() != null ? e.getCause().getMessage() : "none") + ")");
        return "redirect:/";
    }
}
