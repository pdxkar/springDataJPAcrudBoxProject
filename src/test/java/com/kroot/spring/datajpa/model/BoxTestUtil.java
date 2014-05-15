package com.kroot.spring.datajpa.model;

import com.kroot.spring.datajpa.dto.BoxDTO;

/**
* An utility class which contains useful methods for unit testing box related
* functions.
*/
public class BoxTestUtil {

    public static BoxDTO createDTO(Long id, String boxType, String attribute) {
        BoxDTO dto = new BoxDTO();

        dto.setId(id);
        dto.setBoxType(boxType);
        dto.setAttribute(attribute);

        return dto;
    }

    public static Box createModelObject(Long id, String boxType, String attribute) {
        Box model = Box.getBuilder(boxType, attribute).build();

        model.setId(id);

        return model;
    }
}
