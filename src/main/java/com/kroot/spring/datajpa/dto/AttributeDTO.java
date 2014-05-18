package com.kroot.spring.datajpa.dto;

import com.kroot.spring.datajpa.model.Attribute;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class AttributeDTO {

    private Long id;

    @NotEmpty
    private String attributeName;

    //Brenton used constructors like this:
    public AttributeDTO() { }

    public AttributeDTO(Attribute attribute){
        attributeName = attribute.getAttribute();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttribute() {
        return attributeName;
    }

    public void setAttribute(String attribute) {
        this.attributeName = attribute;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
