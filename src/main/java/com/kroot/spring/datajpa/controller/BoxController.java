package com.kroot.spring.datajpa.controller;

import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Box;
import com.kroot.spring.datajpa.model.Attribute;
import com.kroot.spring.datajpa.service.BoxNotFoundException;
import com.kroot.spring.datajpa.service.BoxService;
import com.kroot.spring.datajpa.service.AttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("box")
public class BoxController extends AbstractController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BoxController.class);
    
    protected static final String ERROR_MESSAGE_KEY_DELETED_BOX_WAS_NOT_FOUND = "error.message.deleted.not.found";
    protected static final String ERROR_MESSAGE_KEY_EDITED_BOX_WAS_NOT_FOUND = "error.message.edited.not.found";
    
    protected static final String FEEDBACK_MESSAGE_KEY_BOX_CREATED = "feedback.message.box.created";
    protected static final String FEEDBACK_MESSAGE_KEY_BOX_DELETED = "feedback.message.box.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_BOX_EDITED = "feedback.message.box.edited";
    
    protected static final String MODEL_ATTIRUTE_BOX = "box";
    protected static final String MODEL_ATTRIBUTE_BOXES = "boxes";

    protected static final String MODEL_ATTRIBUTE_ATTRIBUTES = "attributes";

    protected static final String BOX_ADD_FORM_VIEW = "box/create";
    protected static final String BOX_EDIT_FORM_VIEW = "box/edit";
    protected static final String BOX_LIST_VIEW = "box/list";
    
    protected static final String REQUEST_MAPPING_LIST = "/";
    
    @Resource
    private BoxService boxService;

    @Resource
    private AttributeService attributeService;


    /**
     * Processes delete box requests.
     * @param id    The id of the deleted box.
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/box/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        LOGGER.debug("Deleting box with id: " + id);

        try {
            Box deleted = boxService.delete(id);
            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_BOX_DELETED, deleted.getBoxAndAttribute());
        } catch (BoxNotFoundException e) {
            LOGGER.debug("No box found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_DELETED_BOX_WAS_NOT_FOUND);
        }

        return createRedirectViewPath(REQUEST_MAPPING_LIST);
    }

    /**
     * Processes create box requests.
     * @param model
     * @return  The name of the create box form view.
     */
    @RequestMapping(value = "/box/create", method = RequestMethod.GET) 
    public String showCreateBoxForm(Model model) {
        LOGGER.debug("Rendering create box form");
        
        model.addAttribute(MODEL_ATTIRUTE_BOX, new BoxDTO());

        return BOX_ADD_FORM_VIEW;
    }

    /**
     * Processes the submissions of create box form.
     * @param created   The information of the created boxes.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/box/create", method = RequestMethod.POST)
    public String submitCreateBoxForm(@Valid @ModelAttribute(MODEL_ATTIRUTE_BOX) BoxDTO created, BindingResult bindingResult, RedirectAttributes attributes) {
        LOGGER.debug("Create box form was submitted with information: " + created);

        if (bindingResult.hasErrors()) {
            return BOX_ADD_FORM_VIEW;
        }
                
        Box box = boxService.create(created);

        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_BOX_CREATED, box.getBoxAndAttribute());

        return createRedirectViewPath(REQUEST_MAPPING_LIST);
    }

    /**
     * Processes edit box requests.
     * @param id    The id of the edited box.
     * @param model
     * @param attributes
     * @return  The name of the edit box form view.
     */
    @RequestMapping(value = "/box/edit/{id}", method = RequestMethod.GET)
    public String showEditBoxForm(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        LOGGER.debug("Rendering edit box form for box with id: " + id);
        
        Box box = boxService.findById(id);
        if (box == null) {
            LOGGER.debug("No box found with id: " + id);
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_BOX_WAS_NOT_FOUND);
            return createRedirectViewPath(REQUEST_MAPPING_LIST);            
        }

        model.addAttribute(MODEL_ATTIRUTE_BOX, constructFormObject(box));
        
        return BOX_EDIT_FORM_VIEW;
    }

    /**
     * Processes the submissions of edit box form.
     * @param updated   The information of the edited box.
     * @param bindingResult
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/box/edit", method = RequestMethod.POST)
    public String submitEditBoxForm(@Valid @ModelAttribute(MODEL_ATTIRUTE_BOX) BoxDTO updated, BindingResult bindingResult, RedirectAttributes attributes) {
        LOGGER.debug("Edit box form was submitted with information: " + updated);
        
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Edit box form contains validation errors. Rendering form view.");
            return BOX_EDIT_FORM_VIEW;
        }
        
        try {
            Box box = boxService.update(updated);
            addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_BOX_EDITED, box.getBoxAndAttribute());
        } catch (BoxNotFoundException e) {
            LOGGER.debug("No box was found with id: " + updated.getId());
            addErrorMessage(attributes, ERROR_MESSAGE_KEY_EDITED_BOX_WAS_NOT_FOUND);
        }
        
        return createRedirectViewPath(REQUEST_MAPPING_LIST);
    }
    
    private BoxDTO constructFormObject(Box box) {
        BoxDTO formObject = new BoxDTO();
        
        formObject.setId(box.getId());
        formObject.setBoxType(box.getBoxType());
        formObject.setAttribute(box.getAttribute());
        
        return formObject;
    }

    /**
     * Processes requests to home page which lists all available boxes.
     * @param model
     * @return  The name of the box list view.
     */
    @RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
    public String showList(Model model) {
        LOGGER.debug("Rendering box list page");

        List<Box> boxes = boxService.findAll();
        model.addAttribute(MODEL_ATTRIBUTE_BOXES, boxes);

        //this is a test
        List<Attribute> attributes = attributeService.findAll();
        model.addAttribute(MODEL_ATTRIBUTE_ATTRIBUTES, attributes);

        return BOX_LIST_VIEW;
    }

    /**
     * These setter methods should only be used by unit tests
     * @param boxService
     */
    protected void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }

    protected void setAttributeService(AttributeService attributeService) {
        this.attributeService = attributeService;
    }
}
