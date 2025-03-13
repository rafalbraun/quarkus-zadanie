package org.acme.rest.github.dto;

import org.acme.rest.github.model.Branch;

import java.util.List;

public class RepositoryDto {
    public String name;
    public String owner;
    public boolean fork;
    public List<BranchDto> branches;

    public RepositoryDto(String name, String owner, boolean fork, List<Branch> branches) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
        this.branches = branches.stream()
                .map(branch -> new BranchDto(branch.name, branch.commit.sha))
                .toList();
    }
}