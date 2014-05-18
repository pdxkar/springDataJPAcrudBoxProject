package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.AttributeDTO;
import com.kroot.spring.datajpa.model.Attribute;
import com.kroot.spring.datajpa.repository.AttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepositoryAttributeService implements AttributeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryAttributeService.class);

    @Resource
    private AttributeRepository attributeRepository;

//    @Transactional
//    @Override
//    public Attribute create(AttributeDTO created) {
//        LOGGER.debug("Creating a new box with information: " + created);
//
//        Attribute box = Attribute.getBuilder(created.getAttributeType(), created.getAttribute()).build();
//
//        return attributeRepository.save(box);
//    }
//
//    @Transactional(rollbackFor = BoxNotFoundException.class)
//    @Override
//    public Box delete(Long boxId) throws BoxNotFoundException {
//        LOGGER.debug("Deleting box with id: " + boxId);
//
//        Box deleted = boxRepository.findOne(boxId);
//
//        if (deleted == null) {
//            LOGGER.debug("No box found with id: " + boxId);
//            throw new BoxNotFoundException();
//        }
//
//        boxRepository.delete(deleted);
//        return deleted;
//    }

    @Transactional(readOnly = true)
    @Override
    public List<Attribute> findAll() {
        LOGGER.debug("Finding all attributes");
        return attributeRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Attribute findById(Long id) {
        LOGGER.debug("Finding attribute by id: " + id);
        return attributeRepository.findOne(id);
    }

//    @Transactional(rollbackFor = BoxNotFoundException.class)
//    @Override
//    public Box update(BoxDTO updated) throws BoxNotFoundException {
//        LOGGER.debug("Updating box with information: " + updated);
//
//        Box box = boxRepository.findOne(updated.getId());
//
//        if (box == null) {
//            LOGGER.debug("No box found with id: " + updated.getId());
//            throw new BoxNotFoundException();
//        }
//
//        box.update(updated.getBoxType(), updated.getAttribute());
//
//        return box;
//    }

//    /**
//     * This setter method should be used only by unit tests.
//     * @param boxRepository
//     */
//    protected void setBoxRepository(BoxRepository boxRepository) {
//        this.boxRepository = boxRepository;
//    }


}

