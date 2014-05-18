package com.kroot.spring.datajpa.dto;

import com.kroot.spring.datajpa.model.Attribute;
import com.kroot.spring.datajpa.model.Box;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

public class BoxDTO {

    private Long id;

    @NotEmpty
    private String boxType;

    @NotEmpty
    private String attribute;

    private Set<Attribute> attributes;

  //  private List<AttributeDTO> boxAttributes;


//    private List<AttributeDTO> attributes;

//    //brenton had these constructors in place:
//    public BoxDTO() { }
//
//    public BoxDTO(Box box){
//        boxType = box.getBoxType();
//        Set<Attribute> attributes = box.getAttributes();
//        for(Attribute attribute : attributes){
//            boxAttributes.add(new AttributeDTO(attribute));
//        }
//    }

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

    //experiment
    public Set<Attribute> getAttributes() { return attributes; }
    public void setAttributes(Set<Attribute> attributes) {this.attributes = attributes; }
//    public List<AttributeDTO> getAttributes() { return boxAttributes; }
//    public void setAttributes(List<AttributeDTO> attributes) {this.boxAttributes = attributes; }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
