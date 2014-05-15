package com.kroot.spring.datajpa.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class BoxDTO {
    
    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    public BoxDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
