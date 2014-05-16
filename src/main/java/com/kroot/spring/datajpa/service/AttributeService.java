package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.AttributeDTO;
import com.kroot.spring.datajpa.model.Attribute;

import java.util.List;

/**
 * Declares methods used to obtain and modify attribute information.
 */
public interface AttributeService {

//    /**
//     * Creates a new box.
//     * @param created   The information of the created box.
//     * @return  The created box.
//     */
//    public Box create(BoxDTO created);
//
//    /**
//     * Deletes a box.
//     * @param boxId  The id of the deleted box.
//     * @return  The deleted box.
//     * @throws BoxNotFoundException  if no box is found with the given id.
//     */
//    public Box delete(Long boxId) throws BoxNotFoundException;

    /**
     * Finds all attributes.
     * @return  A list of attributes.
     */
    public List<Attribute> findAll();

    /**
     * Finds box by id.
     * @param id    The id of the wanted box.
     * @return  The found box. If no box is found, this method returns null.
     */
    public Attribute findById(Long id);

//    /**
//     * Updates the information of an attribute.
//     * @param updated   The information of the updated attribute.
//     * @return  The updated attribute.
//     * @throws AttributeNotFoundException  if no box is found with given id.
//     */
//    public Attribute update(AttributeDTO updated) throws AttributeNotFoundException;
}

