package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Box;
import com.kroot.spring.datajpa.repository.BoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepositoryBoxService implements BoxService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryBoxService.class);
    
    @Resource
    private BoxRepository boxRepository;

    @Transactional
    @Override
    public Box create(BoxDTO created) {
        LOGGER.debug("Creating a new box with information: " + created);

        //this works
  //      Box box = Box.getBuilder(created.getBoxType(), created.getAttribute()).build();

        //experiment
        Box box = Box.getBuilder(created.getBoxType(), created.getAttribute(), created.getAttributes()).build();
        
        return boxRepository.save(box);
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

    /**
     * This setter method should be used only by unit tests.
     * @param boxRepository
     */
    protected void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }
}
