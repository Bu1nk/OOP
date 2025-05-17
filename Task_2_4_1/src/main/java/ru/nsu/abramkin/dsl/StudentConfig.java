package ru.nsu.abramkin.dsl;

public class StudentConfig {
    private String githubUsername;
    private String fullName;
    private String repositoryUrl;

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    // Getters
    public String getGithubUsername() {
        return githubUsername;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }
} 