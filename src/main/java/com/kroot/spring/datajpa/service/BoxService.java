package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Box;

import java.util.List;

/**
 * Declares methods used to obtain and modify person information.
 * @author Petri Kainulainen
 */
public interface BoxService {

    /**
     * Creates a new person.
     * @param created   The information of the created person.
     * @return  The created person.
     */
    public Box create(BoxDTO created);

    /**
     * Deletes a person.
     * @param personId  The id of the deleted person.
     * @return  The deleted person.
     * @throws BoxNotFoundException  if no person is found with the given id.
     */
    public Box delete(Long personId) throws BoxNotFoundException;

    /**
     * Finds all persons.
     * @return  A list of persons.
     */
    public List<Box> findAll();

    /**
     * Finds person by id.
     * @param id    The id of the wanted person.
     * @return  The found person. If no person is found, this method returns null.
     */
    public Box findById(Long id);

    /**
     * Updates the information of a person.
     * @param updated   The information of the updated person.
     * @return  The updated person.
     * @throws BoxNotFoundException  if no person is found with given id.
     */
    public Box update(BoxDTO updated) throws BoxNotFoundException;
}
