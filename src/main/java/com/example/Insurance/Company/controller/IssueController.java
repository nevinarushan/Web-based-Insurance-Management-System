package com.example.Insurance.Company.controller;

import com.example.Insurance.Company.model.Issue;
import com.example.Insurance.Company.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "http://localhost:3000")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        return issueService.saveIssue(issue);
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @PutMapping("/{id}")
    public Issue updateIssueStatus(@PathVariable Long id, @RequestBody Issue issue) {
        return issueService.updateIssueStatus(id, issue.getStatus());
    }
}
