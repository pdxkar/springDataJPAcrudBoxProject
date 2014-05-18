package com.kroot.spring.datajpa.model;

import com.kroot.spring.datajpa.dto.AttributeDTO;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* An entity class which contains the information of a single attribute.
*/
@Entity
@Table(name = "attribute")
public class Attribute {

//    private Set<Box> boxes = new HashSet<Box>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long attributeId;

    @Column(name = "attribute", nullable = false)
    private String attribute;

    //brenton's way
//    @ManyToMany(mappedBy = "attributes")
//    @LazyCollection(LazyCollectionOption.EXTRA)
//    @Cascade(CascadeType.ALL)
//    private Set<Box> boxes;

    //    pkainulainen's way
    @ManyToMany(mappedBy = "attributes")
    private Set<Box> boxes = new HashSet<Box>();

    //try this constructor stuff that brenton did:
//    public Attribute(){  }
//
//    public Attribute(String attribute){
//        this.attribute = attribute;
//    }
//
//    public Attribute(AttributeDTO attributeDTO){
//        attribute = attributeDTO.getAttribute();
//    }

    public Set<Box> getBoxes() {return this.boxes;}

    public void setBoxes(Set<Box> boxes){
        this.boxes = boxes;
    }

    public String getAttribute() {  return attribute;  }

    public Long getId() {
        return attributeId;
    }
}

