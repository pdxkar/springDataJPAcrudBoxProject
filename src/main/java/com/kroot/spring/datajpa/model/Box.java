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
    
    @Column(name = "creation_time", nullable = false)
    private Date creationTime;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "modification_time", nullable = false)
    private Date modificationTime;

    public Long getId() {
        return id;
    }

    /**
     * Gets a builder which is used to create Box objects.
     * @param firstName The first name of the created user.
     * @param lastName  The last name of the created user.
     * @return  A new Builder instance.
     */
    public static Builder getBuilder(String firstName, String lastName) {
        return new Builder(firstName, lastName);
    }
    
    public Date getCreationTime() {
        return creationTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the full name of the box.
     * @return  The full name of the box.
     */
    @Transient
    public String getName() {
        StringBuilder name = new StringBuilder();
        
        name.append(firstName);
        name.append(" ");
        name.append(lastName);
        
        return name.toString();
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void update(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    @PreUpdate
    public void preUpdate() {
        modificationTime = new Date();
    }
    
    @PrePersist
    public void prePersist() {
        Date now = new Date();
        creationTime = now;
        modificationTime = now;
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
         * @param firstName The first name of the created Box object.
         * @param lastName  The last name of the created Box object.
         */
        Builder(String firstName, String lastName) {
            built = new Box();
            built.firstName = firstName;
            built.lastName = lastName;
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
