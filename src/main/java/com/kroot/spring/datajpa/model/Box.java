package com.kroot.spring.datajpa.model;

import com.kroot.spring.datajpa.dto.AttributeDTO;
import com.kroot.spring.datajpa.dto.BoxDTO;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An entity class which contains the information of a single box.
 */
@Entity
/*@Table(name = "boxes")*/
@Table(name = "box")
public class Box implements Serializable {

//    private Set<Attribute> attributes = new HashSet<Attribute>(0);
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boxId;

    @Column(name = "boxType", nullable = false)
    private String boxType;

    //Brenton's way
//    @ManyToMany
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private Set<Attribute> attributes;


//    pkainulainen's way
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "boxattribute",
    joinColumns = @JoinColumn(name = "boxid"),
    inverseJoinColumns = { @JoinColumn(name = "attributeid")})
    private Set<Attribute> attributes = new HashSet<Attribute>();

    public Set<Attribute> getAttributes(){ return this.attributes; }
 /*   public Set<Attribute> getAttributes(){
        return this.attributeSet;
    }*/

    public void setAttributes(Set<Attribute> attributes){
        this.attributes = attributes;
    }
   /*public void setAttributes(Set<Attribute> attributeSet){
       this.attributeSet = attributeSet;
   }*/

    @Column(name = "attribute", nullable = false)
    private String attribute;

    public Long getId() {
        return boxId;
    }

    /**
     * Gets a builder which is used to create Box objects.
     * @param boxType The type of created box.
     * @param attribute  The attribute of the created box.
     * @return  A new Builder instance.
     */
 //   public static Builder getBuilder(String boxType, String attribute) { return new Builder(boxType, attribute);    }

    //this kind of worked
   public static Builder getBuilder(String boxType, String attribute, Set<Attribute> attributeSet) { return new Builder(boxType, attribute, attributeSet);    }

    //experiment - this worked
//    public static Builder getBuilder(String boxType, String attribute, Set<Attribute> attributes) { return new Builder(boxType, attribute, attributes);    }

    //experiment - this didn't work at all
//    public static Builder getBuilder(String boxType, String attribute, Set<AttributeDTO> attributes) { return new Builder(boxType, attribute, attributes);    }

    public String getBoxType() { return boxType; }

    public String getAttribute() {  return attribute;  }



    /**
     * Gets the box type and attribute.
     * @return  The type and attribute of the box.
     */
    @Transient
    public String getBoxAndAttribute() {
        StringBuilder name = new StringBuilder();
        
        name.append(boxType);
        name.append(" ");
        name.append(attribute);
        
        return name.toString();
    }

    public void update(String boxType, String attribute) {
        this.boxType = boxType;
        this.attribute = attribute;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * A Builder class used to create new Box objects.
     */
    public static class Builder {
        Box built;

        /**
         * Creates a new Builder instance.
         * @param boxType The type of the created Box object.
         * @param attribute  The attribute the created Box object.
         */
 //       Builder(String boxType, String attribute) {
        Builder(String boxType, String attribute, Set<Attribute> attributes) {
            built = new Box();
            built.boxType = boxType;
            built.attribute = attribute;
            built.attributes = attributes;
        }

        /**
         * Builds the new Box object.
         * @return  The created Box object.
         */
        public Box build() {
            return built;
        }


    }

    //try this constructor stuff brenton did:
//    public Box() {}
//
//    public Box(String boxType){
//        this.boxType = boxType;
//    }
//
//    //brenton has this kind of constructor:
//    public Box(BoxDTO boxDTO){
//        this.boxType = boxDTO.getBoxType();
//
//        if(boxDTO.getAttributes() != null){
//            this.attributes = new HashSet<Attribute>(boxDTO.getAttributes().size());
//            for(AttributeDTO attributeDTO : boxDTO.getAttributes()){
//                this.attributes.add(new Attribute(attributeDTO));
//            }
//        }
//    }

    /**
     * This setter method should only be used by unit tests.
     * @param id
     */
    protected void setId(Long id) {
        this.boxId = id;
    }
}
