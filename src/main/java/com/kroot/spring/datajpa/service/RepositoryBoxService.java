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
        
        Box box = Box.getBuilder(created.getFirstName(), created.getLastName()).build();
        
        return boxRepository.save(box);
    }

    @Transactional(rollbackFor = BoxNotFoundException.class)
    @Override
    public Box delete(Long personId) throws BoxNotFoundException {
        LOGGER.debug("Deleting person with id: " + personId);
        
        Box deleted = boxRepository.findOne(personId);
        
        if (deleted == null) {
            LOGGER.debug("No person found with id: " + personId);
            throw new BoxNotFoundException();
        }
        
        boxRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Box> findAll() {
        LOGGER.debug("Finding all persons");
        return boxRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Box findById(Long id) {
        LOGGER.debug("Finding person by id: " + id);
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
        
        box.update(updated.getFirstName(), updated.getLastName());

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
