package com.example.Insurance.Company.repository;

import com.example.Insurance.Company.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
