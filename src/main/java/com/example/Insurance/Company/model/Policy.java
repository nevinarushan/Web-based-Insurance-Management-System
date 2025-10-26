
/**
 * This class represents an insurance policy in the application.
 * It is an entity class that is mapped to the "policy" table in the database.
 * The class includes information about the policy, such as the policy number, title, description, and image path.
 */
package com.example.Insurance.Company.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Policy {

    @Id
    @Column(name = "policynum")
    private String policynum;

    @Column(name = "title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    public Policy() {
    }

    public Policy(String policynum, String title, String description, String imagePath) {
        this.policynum = policynum;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getPolicynum() {
        return policynum;
    }

    public void setPolicynum(String policynum) {
        this.policynum = policynum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
