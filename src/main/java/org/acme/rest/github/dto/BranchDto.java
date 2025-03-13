package org.acme.rest.github.dto;

public class BranchDto {
    public String name;
    public String lastCommitSha;

    public BranchDto(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }
}
