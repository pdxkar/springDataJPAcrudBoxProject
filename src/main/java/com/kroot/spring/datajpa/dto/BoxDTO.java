package com.kroot.spring.datajpa.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class BoxDTO {
    
    private Long id;

    @NotEmpty
    private String boxType;

    @NotEmpty
    private String attribute;


//    private List<AttributeDTO> attributes;

    public BoxDTO() {

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getBoxType() {
        return boxType;
    }
    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getAttribute() {
        return attribute;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

//    public List<AttributeDTO> getAttributes() { return attributes; }
//    public void setAttributes(List<AttributeDTO> attributes) {this.attributes = attributes; }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
