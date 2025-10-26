package com.example.Insurance.Company.service;

import com.example.Insurance.Company.model.Issue;
import com.example.Insurance.Company.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public Issue saveIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Issue updateIssueStatus(Long id, String status) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
