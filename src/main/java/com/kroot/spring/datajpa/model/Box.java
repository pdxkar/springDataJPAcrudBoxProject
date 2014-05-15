package com.kroot.spring.datajpa.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * An entity class which contains the information of a single box.
 */
@Entity
/*@Table(name = "boxes")*/
@Table(name = "person")
public class Box {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "boxType", nullable = false)
    private String boxType;

    @Column(name = "attribute", nullable = false)
    private String attribute;

    public Long getId() {
        return id;
    }

    /**
     * Gets a builder which is used to create Box objects.
     * @param boxType The type of created box.
     * @param attribute  The attribute of the created box.
     * @return  A new Builder instance.
     */
    public static Builder getBuilder(String boxType, String attribute) {
        return new Builder(boxType, attribute);
    }

    public String getBoxType() {
        return boxType;
    }

    public String getAttribute() {
        return attribute;
    }

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
        Builder(String boxType, String attribute) {
            built = new Box();
            built.boxType = boxType;
            built.attribute = attribute;
        }

        /**
         * Builds the new Box object.
         * @return  The created Box object.
         */
        public Box build() {
            return built;
        }
    }

    /**
     * This setter method should only be used by unit tests.
     * @param id
     */
    protected void setId(Long id) {
        this.id = id;
    }
}
