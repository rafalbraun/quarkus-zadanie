package org.acme.rest.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {
    public String name;
    public Owner owner;
    public boolean fork;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isFork() { return fork; }
    public void setFork(boolean fork) { this.fork = fork; }

}
