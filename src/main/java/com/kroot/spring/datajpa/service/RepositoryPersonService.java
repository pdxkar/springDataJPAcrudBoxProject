package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Box;
import com.kroot.spring.datajpa.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepositoryPersonService implements PersonService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryPersonService.class);
    
    @Resource
    private PersonRepository personRepository;

    @Transactional
    @Override
    public Box create(BoxDTO created) {
        LOGGER.debug("Creating a new box with information: " + created);
        
        Box box = Box.getBuilder(created.getFirstName(), created.getLastName()).build();
        
        return personRepository.save(box);
    }

    @Transactional(rollbackFor = PersonNotFoundException.class)
    @Override
    public Box delete(Long personId) throws PersonNotFoundException {
        LOGGER.debug("Deleting person with id: " + personId);
        
        Box deleted = personRepository.findOne(personId);
        
        if (deleted == null) {
            LOGGER.debug("No person found with id: " + personId);
            throw new PersonNotFoundException();
        }
        
        personRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Box> findAll() {
        LOGGER.debug("Finding all persons");
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Box findById(Long id) {
        LOGGER.debug("Finding person by id: " + id);
        return personRepository.findOne(id);
    }

    @Transactional(rollbackFor = PersonNotFoundException.class)
    @Override
    public Box update(BoxDTO updated) throws PersonNotFoundException {
        LOGGER.debug("Updating box with information: " + updated);
        
        Box box = personRepository.findOne(updated.getId());
        
        if (box == null) {
            LOGGER.debug("No box found with id: " + updated.getId());
            throw new PersonNotFoundException();
        }
        
        box.update(updated.getFirstName(), updated.getLastName());

        return box;
    }

    /**
     * This setter method should be used only by unit tests.
     * @param personRepository
     */
    protected void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
