package com.kroot.spring.datajpa.controller;

import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Box;
import com.kroot.spring.datajpa.model.BoxTestUtil;
import com.kroot.spring.datajpa.service.BoxNotFoundException;
import com.kroot.spring.datajpa.service.BoxService;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.*;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

public class BoxControllerTest extends AbstractTestController {

    private static final String FIELD_NAME_FIRST_NAME = "firstName";
    private static final String FIELD_NAME_LAST_NAME = "lastName";

    private static final Long BOX_ID = Long.valueOf(5);
    private static final String FIRST_NAME = "Foo";
    private static final String FIRST_NAME_UPDATED = "FooUpdated";
    private static final String LAST_NAME = "Bar";
    private static final String LAST_NAME_UPDATED = "BarUpdated";

    private BoxController controller;

    private BoxService boxServiceMock;

    @Override
    public void setUpTest() {
        controller = new BoxController();

        controller.setMessageSource(getMessageSourceMock());

        boxServiceMock = mock(BoxService.class);
        controller.setBoxService(boxServiceMock);
    }

    @Test
    public void delete() throws BoxNotFoundException {
        Box deleted = BoxTestUtil.createModelObject(BOX_ID, FIRST_NAME, LAST_NAME);
        when(boxServiceMock.delete(BOX_ID)).thenReturn(deleted);

        initMessageSourceForFeedbackMessage(BoxController.FEEDBACK_MESSAGE_KEY_BOX_DELETED);

        RedirectAttributes attributes = new RedirectAttributesModelMap();
        String view = controller.delete(BOX_ID, attributes);

        verify(boxServiceMock, times(1)).delete(BOX_ID);
        verifyNoMoreInteractions(boxServiceMock);
        assertFeedbackMessage(attributes, BoxController.FEEDBACK_MESSAGE_KEY_BOX_DELETED);

        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
        assertEquals(expectedView, view);
    }

    @Test
    public void deleteWhenBoxIsNotFound() throws BoxNotFoundException {
        when(boxServiceMock.delete(BOX_ID)).thenThrow(new BoxNotFoundException());

        initMessageSourceForErrorMessage(BoxController.ERROR_MESSAGE_KEY_DELETED_BOX_WAS_NOT_FOUND);

        RedirectAttributes attributes = new RedirectAttributesModelMap();
        String view = controller.delete(BOX_ID, attributes);

        verify(boxServiceMock, times(1)).delete(BOX_ID);
        verifyNoMoreInteractions(boxServiceMock);
        assertErrorMessage(attributes, BoxController.ERROR_MESSAGE_KEY_DELETED_BOX_WAS_NOT_FOUND);

        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
        assertEquals(expectedView, view);
    }

    @Test
    public void showCreateBoxForm() {
        Model model = new BindingAwareModelMap();

        String view = controller.showCreateBoxForm(model);

        verifyZeroInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_ADD_FORM_VIEW, view);

        BoxDTO added = (BoxDTO) model.asMap().get(BoxController.MODEL_ATTIRUTE_BOX);
        assertNotNull(added);

        assertNull(added.getId());
        assertNull(added.getFirstName());
        assertNull(added.getLastName());
    }

    @Test
    public void submitCreateBoxForm() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/create", "POST");

        BoxDTO created = BoxTestUtil.createDTO(BOX_ID, FIRST_NAME, LAST_NAME);
        Box model = BoxTestUtil.createModelObject(BOX_ID, FIRST_NAME, LAST_NAME);
        when(boxServiceMock.create(created)).thenReturn(model);

        initMessageSourceForFeedbackMessage(BoxController.FEEDBACK_MESSAGE_KEY_BOX_CREATED);

        RedirectAttributes attributes = new RedirectAttributesModelMap();
        BindingResult result = bindAndValidate(mockRequest, created);

        String view = controller.submitCreateBoxForm(created, result, attributes);

        verify(boxServiceMock, times(1)).create(created);
        verifyNoMoreInteractions(boxServiceMock);

        String expectedViewPath = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
        assertEquals(expectedViewPath, view);

        assertFeedbackMessage(attributes, BoxController.FEEDBACK_MESSAGE_KEY_BOX_CREATED);

        verify(boxServiceMock, times(1)).create(created);
        verifyNoMoreInteractions(boxServiceMock);
    }

    @Test
    public void submitEmptyCreateBoxForm() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/create", "POST");

        BoxDTO created = new BoxDTO();

        RedirectAttributes attributes = new RedirectAttributesModelMap();
        BindingResult result = bindAndValidate(mockRequest, created);

        String view = controller.submitCreateBoxForm(created, result, attributes);

        verifyZeroInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_ADD_FORM_VIEW, view);
        assertFieldErrors(result, FIELD_NAME_FIRST_NAME, FIELD_NAME_LAST_NAME);
    }

    @Test
    public void submitCreateBoxFormWithEmptyFirstName() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/create", "POST");

        BoxDTO created = BoxTestUtil.createDTO(null, null, LAST_NAME);

        RedirectAttributes attributes = new RedirectAttributesModelMap();
        BindingResult result = bindAndValidate(mockRequest, created);

        String view = controller.submitCreateBoxForm(created, result, attributes);

        verifyZeroInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_ADD_FORM_VIEW, view);
        assertFieldErrors(result, FIELD_NAME_FIRST_NAME);
    }

    @Test
    public void submitCreateBoxFormWithEmptyLastName() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/create", "POST");

        BoxDTO created = BoxTestUtil.createDTO(null, FIRST_NAME, null);

        RedirectAttributes attributes = new RedirectAttributesModelMap();
        BindingResult result = bindAndValidate(mockRequest, created);

        String view = controller.submitCreateBoxForm(created, result, attributes);

        verifyZeroInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_ADD_FORM_VIEW, view);
        assertFieldErrors(result, FIELD_NAME_LAST_NAME);
    }

    @Test
    public void showEditBoxForm() {
        Box box = BoxTestUtil.createModelObject(BOX_ID, FIRST_NAME, LAST_NAME);
        when(boxServiceMock.findById(BOX_ID)).thenReturn(box);

        Model model = new BindingAwareModelMap();
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.showEditBoxForm(BOX_ID, model, attributes);

        verify(boxServiceMock, times(1)).findById(BOX_ID);
        verifyNoMoreInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_EDIT_FORM_VIEW, view);

        BoxDTO formObject = (BoxDTO) model.asMap().get(BoxController.MODEL_ATTIRUTE_BOX);

        assertNotNull(formObject);
        assertEquals(box.getId(), formObject.getId());
        assertEquals(box.getFirstName(), formObject.getFirstName());
        assertEquals(box.getLastName(), formObject.getLastName());
    }

    @Test
    public void showEditBoxFormWhenBoxIsNotFound() {
        when(boxServiceMock.findById(BOX_ID)).thenReturn(null);

        initMessageSourceForErrorMessage(BoxController.ERROR_MESSAGE_KEY_EDITED_BOX_WAS_NOT_FOUND);

        Model model = new BindingAwareModelMap();
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.showEditBoxForm(BOX_ID, model, attributes);

        verify(boxServiceMock, times(1)).findById(BOX_ID);
        verifyNoMoreInteractions(boxServiceMock);

        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
        assertEquals(expectedView, view);

        assertErrorMessage(attributes, BoxController.ERROR_MESSAGE_KEY_EDITED_BOX_WAS_NOT_FOUND);
    }

    @Test
    public void submitEditBoxForm() throws BoxNotFoundException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/edit", "POST");
        BoxDTO updated = BoxTestUtil.createDTO(BOX_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
        Box box = BoxTestUtil.createModelObject(BOX_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);

        when(boxServiceMock.update(updated)).thenReturn(box);

        initMessageSourceForFeedbackMessage(BoxController.FEEDBACK_MESSAGE_KEY_BOX_EDITED);

        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.submitEditBoxForm(updated, bindingResult, attributes);

        verify(boxServiceMock, times(1)).update(updated);
        verifyNoMoreInteractions(boxServiceMock);

        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
        assertEquals(expectedView, view);

        assertFeedbackMessage(attributes, BoxController.FEEDBACK_MESSAGE_KEY_BOX_EDITED);

        assertEquals(updated.getFirstName(), box.getFirstName());
        assertEquals(updated.getLastName(), box.getLastName());
    }

    @Test
    public void submitEditBoxFormWhenBoxIsNotFound() throws BoxNotFoundException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/edit", "POST");
        BoxDTO updated = BoxTestUtil.createDTO(BOX_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);

        when(boxServiceMock.update(updated)).thenThrow(new BoxNotFoundException());
        initMessageSourceForErrorMessage(BoxController.ERROR_MESSAGE_KEY_EDITED_BOX_WAS_NOT_FOUND);

        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.submitEditBoxForm(updated, bindingResult, attributes);

        verify(boxServiceMock, times(1)).update(updated);
        verifyNoMoreInteractions(boxServiceMock);

        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
        assertEquals(expectedView, view);

        assertErrorMessage(attributes, BoxController.ERROR_MESSAGE_KEY_EDITED_BOX_WAS_NOT_FOUND);
    }

    @Test
    public void submitEmptyEditBoxForm() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/edit", "POST");
        BoxDTO updated = BoxTestUtil.createDTO(BOX_ID, null, null);

        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.submitEditBoxForm(updated, bindingResult, attributes);

        verifyZeroInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_EDIT_FORM_VIEW, view);
        assertFieldErrors(bindingResult, FIELD_NAME_FIRST_NAME, FIELD_NAME_LAST_NAME);
    }

    @Test
    public void submitEditBoxFormWhenFirstNameIsEmpty() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/edit", "POST");
        BoxDTO updated = BoxTestUtil.createDTO(BOX_ID, null, LAST_NAME_UPDATED);

        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.submitEditBoxForm(updated, bindingResult, attributes);

        verifyZeroInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_EDIT_FORM_VIEW, view);
        assertFieldErrors(bindingResult, FIELD_NAME_FIRST_NAME);
    }

    @Test
    public void submitEditBoxFormWhenLastNameIsEmpty() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/edit", "POST");
        BoxDTO updated = BoxTestUtil.createDTO(BOX_ID, FIRST_NAME_UPDATED, null);

        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.submitEditBoxForm(updated, bindingResult, attributes);

        verifyZeroInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_EDIT_FORM_VIEW, view);
        assertFieldErrors(bindingResult, FIELD_NAME_LAST_NAME);
    }

    @Test
    public void showList() {
        List<Box> boxes = new ArrayList<Box>();
        when(boxServiceMock.findAll()).thenReturn(boxes);

        Model model = new BindingAwareModelMap();
        String view = controller.showList(model);

        verify(boxServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(boxServiceMock);

        assertEquals(BoxController.BOX_LIST_VIEW, view);
        assertEquals(boxes, model.asMap().get(BoxController.MODEL_ATTRIBUTE_BOXES));
    }
}
