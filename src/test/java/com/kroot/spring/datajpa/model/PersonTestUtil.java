package com.kroot.spring.datajpa.model;

import com.kroot.spring.datajpa.dto.BoxDTO;

/**
 * An utility class which contains useful methods for unit testing person related
 * functions.
 */
public class PersonTestUtil {

    public static BoxDTO createDTO(Long id, String firstName, String lastName) {
        BoxDTO dto = new BoxDTO();

        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);

        return dto;
    }

    public static Box createModelObject(Long id, String firstName, String lastName) {
        Box model = Box.getBuilder(firstName, lastName).build();

        model.setId(id);

        return model;
    }
}
