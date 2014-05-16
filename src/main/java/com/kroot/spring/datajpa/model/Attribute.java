package com.kroot.spring.datajpa.model;

import com.kroot.spring.datajpa.dto.AttributeDTO;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
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
    private Long id;

    @Column(name = "attribute", nullable = false)
    private String attribute;

//    public Attribute(AttributeDTO attributeDto){
//
//    }

    @ManyToMany(mappedBy = "attributes")
    private Set<Box> boxes = new HashSet<Box>();

    public Set<Box> getBoxes() {return this.boxes;}

    public void setBoxes(Set<Box> boxes){
        this.boxes = boxes;
    }

    public Long getId() {
        return id;
    }
}

