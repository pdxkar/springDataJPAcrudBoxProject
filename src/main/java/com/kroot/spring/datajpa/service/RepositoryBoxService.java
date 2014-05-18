package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.AttributeDTO;
import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Attribute;
import com.kroot.spring.datajpa.model.Box;
import com.kroot.spring.datajpa.repository.AttributeRepository;
import com.kroot.spring.datajpa.repository.BoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RepositoryBoxService implements BoxService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryBoxService.class);

    //Brenton autowired this:
    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeService attributeService;

//        @Transactional
//    @Override
//    public Box create(BoxDTO created) {
//        LOGGER.debug("Creating a new box with information: " + created);
//
//        //this works
//     //   Box box = Box.getBuilder(created.getBoxType(), created.getAttribute()).build();
//
////        List<AttributeDTO> cannedListOfDTOs = new ArrayList<AttributeDTO>();
////        cannedListOfDTOs.add(new AttributeDTO());
////        cannedListOfDTOs.add(new AttributeDTO());
//
//          Set<Attribute> cannedSetOfAttributes = new HashSet<Attribute>();
//            cannedSetOfAttributes.add(new Attribute());
//            cannedSetOfAttributes.add(new Attribute());
//
////
////        //experiment
//            Box box = Box.getBuilder(created.getBoxType(), created.getAttribute(), cannedSetOfAttributes).build();
////        Box box = Box.getBuilder(created.getBoxType(), created.getAttribute(), cannedListOfAttributes).build();
//
//        return boxRepository.save(box);
//
//        //temporarily trying to make method compile
//        //return null;
//    }

    
//    @Resource
//    private BoxRepository boxRepository;

    //this is  pkainulainen's way
    @Transactional
    @Override
    public Box create(BoxDTO created) {
        LOGGER.debug("Creating a new box with information: " + created);

        //this works
     //   Box box = Box.getBuilder(created.getBoxType(), created.getAttribute()).build();

        //experiment
        Box box = Box.getBuilder(created.getBoxType(), created.getAttribute(), created.getAttributes()).build();

        return boxRepository.save(box);

        //temporarily trying to make method compile
        //return null;
    }

    @Transactional(rollbackFor = BoxNotFoundException.class)
    @Override
    public Box delete(Long boxId) throws BoxNotFoundException {
        LOGGER.debug("Deleting box with id: " + boxId);
        
        Box deleted = boxRepository.findOne(boxId);
        
        if (deleted == null) {
            LOGGER.debug("No box found with id: " + boxId);
            throw new BoxNotFoundException();
        }
        
        boxRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Box> findAll() {
        LOGGER.debug("Finding all boxes");
        return boxRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Box findById(Long id) {
        LOGGER.debug("Finding box by id: " + id);
        return boxRepository.findOne(id);
    }

    @Transactional(rollbackFor = BoxNotFoundException.class)
    @Override
    public Box update(BoxDTO updated) throws BoxNotFoundException {
        LOGGER.debug("Updating box with information: " + updated);
        
        Box box = boxRepository.findOne(updated.getId());
        
        if (box == null) {
            LOGGER.debug("No box found with id: " + updated.getId());
            throw new BoxNotFoundException();
        }
        
        box.update(updated.getBoxType(), updated.getAttribute());

        return box;
    }

    //These conversion methods are how Brenton did it:
//    private List<BoxDTO> convertBoxListToBoxDTOList(List<Box> boxes){
//        List<BoxDTO> boxDTOs = new ArrayList<BoxDTO>();
//
//        for(Box box : boxes){
//            boxDTOs.add(convertBoxToDTO(box));
//        }
//        return boxDTOs;
//    }
//
//    //This is another conversion method that Brenton used:
//    private BoxDTO convertBoxToDTO(Box box){
//        BoxDTO boxDTO = new BoxDTO(box);
//
//        List<Attribute> attributes = attributeRepository.findAll();
//
//        List<AttributeDTO> attributeDTOs = new ArrayList<AttributeDTO>(attributes.size());
//
//        for(Attribute attribute : attributes){
//            attributeDTOs.add(new AttributeDTO(attribute));
//        }
//
//        boxDTO.setAttributes(attributeDTOs);
//
//        return boxDTO;
//    }

    /**
     * This setter method should be used only by unit tests.
     * @param boxRepository
     */
/*    protected void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }*/
}
