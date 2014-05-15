package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Box;

import java.util.List;

/**
 * Declares methods used to obtain and modify box information.
 */
public interface BoxService {

    /**
     * Creates a new box.
     * @param created   The information of the created box.
     * @return  The created box.
     */
    public Box create(BoxDTO created);

    /**
     * Deletes a box.
     * @param boxId  The id of the deleted box.
     * @return  The deleted box.
     * @throws BoxNotFoundException  if no box is found with the given id.
     */
    public Box delete(Long boxId) throws BoxNotFoundException;

    /**
     * Finds all boxes.
     * @return  A list of boxes.
     */
    public List<Box> findAll();

    /**
     * Finds box by id.
     * @param id    The id of the wanted box.
     * @return  The found box. If no box is found, this method returns null.
     */
    public Box findById(Long id);

    /**
     * Updates the information of a box.
     * @param updated   The information of the updated box.
     * @return  The updated box.
     * @throws BoxNotFoundException  if no box is found with given id.
     */
    public Box update(BoxDTO updated) throws BoxNotFoundException;
}
