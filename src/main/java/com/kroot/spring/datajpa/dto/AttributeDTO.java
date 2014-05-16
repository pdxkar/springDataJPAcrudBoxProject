package com.kroot.spring.datajpa.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class AttributeDTO {

    private Long id;

    @NotEmpty
    private String attribute;

    public AttributeDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
