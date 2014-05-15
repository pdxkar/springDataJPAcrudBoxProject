//package com.kroot.spring.datajpa.controller;
//
//import com.kroot.spring.datajpa.dto.BoxDTO;
//import com.kroot.spring.datajpa.model.Box;
//import com.kroot.spring.datajpa.model.PersonTestUtil;
//import com.kroot.spring.datajpa.service.BoxNotFoundException;
//import com.kroot.spring.datajpa.service.BoxService;
//import org.junit.Test;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.support.BindingAwareModelMap;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
//
//import java.util.*;
//
//import static junit.framework.Assert.*;
//import static org.mockito.Mockito.*;
//
//public class BoxControllerTest extends AbstractTestController {
//
//    private static final String FIELD_NAME_FIRST_NAME = "firstName";
//    private static final String FIELD_NAME_LAST_NAME = "lastName";
//
//    private static final Long PERSON_ID = Long.valueOf(5);
//    private static final String FIRST_NAME = "Foo";
//    private static final String FIRST_NAME_UPDATED = "FooUpdated";
//    private static final String LAST_NAME = "Bar";
//    private static final String LAST_NAME_UPDATED = "BarUpdated";
//
//    private BoxController controller;
//
//    private BoxService boxServiceMock;
//
//    @Override
//    public void setUpTest() {
//        controller = new BoxController();
//
//        controller.setMessageSource(getMessageSourceMock());
//
//        boxServiceMock = mock(BoxService.class);
//        controller.setBoxService(boxServiceMock);
//    }
//
//    @Test
//    public void delete() throws BoxNotFoundException {
//        Box deleted = PersonTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
//        when(boxServiceMock.delete(PERSON_ID)).thenReturn(deleted);
//
//        initMessageSourceForFeedbackMessage(BoxController.FEEDBACK_MESSAGE_KEY_PERSON_DELETED);
//
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//        String view = controller.delete(PERSON_ID, attributes);
//
//        verify(boxServiceMock, times(1)).delete(PERSON_ID);
//        verifyNoMoreInteractions(boxServiceMock);
//        assertFeedbackMessage(attributes, BoxController.FEEDBACK_MESSAGE_KEY_PERSON_DELETED);
//
//        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
//        assertEquals(expectedView, view);
//    }
//
//    @Test
//    public void deleteWhenPersonIsNotFound() throws BoxNotFoundException {
//        when(boxServiceMock.delete(PERSON_ID)).thenThrow(new BoxNotFoundException());
//
//        initMessageSourceForErrorMessage(BoxController.ERROR_MESSAGE_KEY_DELETED_PERSON_WAS_NOT_FOUND);
//
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//        String view = controller.delete(PERSON_ID, attributes);
//
//        verify(boxServiceMock, times(1)).delete(PERSON_ID);
//        verifyNoMoreInteractions(boxServiceMock);
//        assertErrorMessage(attributes, BoxController.ERROR_MESSAGE_KEY_DELETED_PERSON_WAS_NOT_FOUND);
//
//        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
//        assertEquals(expectedView, view);
//    }
//
//    @Test
//    public void showCreatePersonForm() {
//        Model model = new BindingAwareModelMap();
//
//        String view = controller.showCreatePersonForm(model);
//
//        verifyZeroInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_ADD_FORM_VIEW, view);
//
//        BoxDTO added = (BoxDTO) model.asMap().get(BoxController.MODEL_ATTIRUTE_PERSON);
//        assertNotNull(added);
//
//        assertNull(added.getId());
//        assertNull(added.getFirstName());
//        assertNull(added.getLastName());
//    }
//
//    @Test
//    public void submitCreatePersonForm() {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/person/create", "POST");
//
//        BoxDTO created = PersonTestUtil.createDTO(PERSON_ID, FIRST_NAME, LAST_NAME);
//        Box model = PersonTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
//        when(boxServiceMock.create(created)).thenReturn(model);
//
//        initMessageSourceForFeedbackMessage(BoxController.FEEDBACK_MESSAGE_KEY_PERSON_CREATED);
//
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//        BindingResult result = bindAndValidate(mockRequest, created);
//
//        String view = controller.submitCreatePersonForm(created, result, attributes);
//
//        verify(boxServiceMock, times(1)).create(created);
//        verifyNoMoreInteractions(boxServiceMock);
//
//        String expectedViewPath = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
//        assertEquals(expectedViewPath, view);
//
//        assertFeedbackMessage(attributes, BoxController.FEEDBACK_MESSAGE_KEY_PERSON_CREATED);
//
//        verify(boxServiceMock, times(1)).create(created);
//        verifyNoMoreInteractions(boxServiceMock);
//    }
//
//    @Test
//    public void submitEmptyCreatePersonForm() {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/person/create", "POST");
//
//        BoxDTO created = new BoxDTO();
//
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//        BindingResult result = bindAndValidate(mockRequest, created);
//
//        String view = controller.submitCreatePersonForm(created, result, attributes);
//
//        verifyZeroInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_ADD_FORM_VIEW, view);
//        assertFieldErrors(result, FIELD_NAME_FIRST_NAME, FIELD_NAME_LAST_NAME);
//    }
//
//    @Test
//    public void submitCreatePersonFormWithEmptyFirstName() {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/person/create", "POST");
//
//        BoxDTO created = PersonTestUtil.createDTO(null, null, LAST_NAME);
//
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//        BindingResult result = bindAndValidate(mockRequest, created);
//
//        String view = controller.submitCreatePersonForm(created, result, attributes);
//
//        verifyZeroInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_ADD_FORM_VIEW, view);
//        assertFieldErrors(result, FIELD_NAME_FIRST_NAME);
//    }
//
//    @Test
//    public void submitCreatePersonFormWithEmptyLastName() {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/person/create", "POST");
//
//        BoxDTO created = PersonTestUtil.createDTO(null, FIRST_NAME, null);
//
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//        BindingResult result = bindAndValidate(mockRequest, created);
//
//        String view = controller.submitCreatePersonForm(created, result, attributes);
//
//        verifyZeroInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_ADD_FORM_VIEW, view);
//        assertFieldErrors(result, FIELD_NAME_LAST_NAME);
//    }
//
//    @Test
//    public void showEditPersonForm() {
//        Box box = PersonTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
//        when(boxServiceMock.findById(PERSON_ID)).thenReturn(box);
//
//        Model model = new BindingAwareModelMap();
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//
//        String view = controller.showEditPersonForm(PERSON_ID, model, attributes);
//
//        verify(boxServiceMock, times(1)).findById(PERSON_ID);
//        verifyNoMoreInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_EDIT_FORM_VIEW, view);
//
//        BoxDTO formObject = (BoxDTO) model.asMap().get(BoxController.MODEL_ATTIRUTE_PERSON);
//
//        assertNotNull(formObject);
//        assertEquals(box.getId(), formObject.getId());
//        assertEquals(box.getFirstName(), formObject.getFirstName());
//        assertEquals(box.getLastName(), formObject.getLastName());
//    }
//
//    @Test
//    public void showEditPersonFormWhenPersonIsNotFound() {
//        when(boxServiceMock.findById(PERSON_ID)).thenReturn(null);
//
//        initMessageSourceForErrorMessage(BoxController.ERROR_MESSAGE_KEY_EDITED_PERSON_WAS_NOT_FOUND);
//
//        Model model = new BindingAwareModelMap();
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//
//        String view = controller.showEditPersonForm(PERSON_ID, model, attributes);
//
//        verify(boxServiceMock, times(1)).findById(PERSON_ID);
//        verifyNoMoreInteractions(boxServiceMock);
//
//        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
//        assertEquals(expectedView, view);
//
//        assertErrorMessage(attributes, BoxController.ERROR_MESSAGE_KEY_EDITED_PERSON_WAS_NOT_FOUND);
//    }
//
//    @Test
//    public void submitEditPersonForm() throws BoxNotFoundException {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/box/edit", "POST");
//        BoxDTO updated = PersonTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
//        Box box = PersonTestUtil.createModelObject(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
//
//        when(boxServiceMock.update(updated)).thenReturn(box);
//
//        initMessageSourceForFeedbackMessage(BoxController.FEEDBACK_MESSAGE_KEY_PERSON_EDITED);
//
//        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//
//        String view = controller.submitEditPersonForm(updated, bindingResult, attributes);
//
//        verify(boxServiceMock, times(1)).update(updated);
//        verifyNoMoreInteractions(boxServiceMock);
//
//        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
//        assertEquals(expectedView, view);
//
//        assertFeedbackMessage(attributes, BoxController.FEEDBACK_MESSAGE_KEY_PERSON_EDITED);
//
//        assertEquals(updated.getFirstName(), box.getFirstName());
//        assertEquals(updated.getLastName(), box.getLastName());
//    }
//
//    @Test
//    public void submitEditPersonFormWhenPersonIsNotFound() throws BoxNotFoundException {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/person/edit", "POST");
//        BoxDTO updated = PersonTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
//
//        when(boxServiceMock.update(updated)).thenThrow(new BoxNotFoundException());
//        initMessageSourceForErrorMessage(BoxController.ERROR_MESSAGE_KEY_EDITED_PERSON_WAS_NOT_FOUND);
//
//        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//
//        String view = controller.submitEditPersonForm(updated, bindingResult, attributes);
//
//        verify(boxServiceMock, times(1)).update(updated);
//        verifyNoMoreInteractions(boxServiceMock);
//
//        String expectedView = createExpectedRedirectViewPath(BoxController.REQUEST_MAPPING_LIST);
//        assertEquals(expectedView, view);
//
//        assertErrorMessage(attributes, BoxController.ERROR_MESSAGE_KEY_EDITED_PERSON_WAS_NOT_FOUND);
//    }
//
//    @Test
//    public void submitEmptyEditPersonForm() {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/person/edit", "POST");
//        BoxDTO updated = PersonTestUtil.createDTO(PERSON_ID, null, null);
//
//        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//
//        String view = controller.submitEditPersonForm(updated, bindingResult, attributes);
//
//        verifyZeroInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_EDIT_FORM_VIEW, view);
//        assertFieldErrors(bindingResult, FIELD_NAME_FIRST_NAME, FIELD_NAME_LAST_NAME);
//    }
//
//    @Test
//    public void submitEditPersonFormWhenFirstNameIsEmpty() {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/person/edit", "POST");
//        BoxDTO updated = PersonTestUtil.createDTO(PERSON_ID, null, LAST_NAME_UPDATED);
//
//        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//
//        String view = controller.submitEditPersonForm(updated, bindingResult, attributes);
//
//        verifyZeroInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_EDIT_FORM_VIEW, view);
//        assertFieldErrors(bindingResult, FIELD_NAME_FIRST_NAME);
//    }
//
//    @Test
//    public void submitEditPersonFormWhenLastNameIsEmpty() {
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest("/person/edit", "POST");
//        BoxDTO updated = PersonTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, null);
//
//        BindingResult bindingResult = bindAndValidate(mockRequest, updated);
//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//
//        String view = controller.submitEditPersonForm(updated, bindingResult, attributes);
//
//        verifyZeroInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_EDIT_FORM_VIEW, view);
//        assertFieldErrors(bindingResult, FIELD_NAME_LAST_NAME);
//    }
//
//    @Test
//    public void showList() {
//        List<Box> boxes = new ArrayList<Box>();
//        when(boxServiceMock.findAll()).thenReturn(boxes);
//
//        Model model = new BindingAwareModelMap();
//        String view = controller.showList(model);
//
//        verify(boxServiceMock, times(1)).findAll();
//        verifyNoMoreInteractions(boxServiceMock);
//
//        assertEquals(BoxController.PERSON_LIST_VIEW, view);
//        assertEquals(boxes, model.asMap().get(BoxController.MODEL_ATTRIBUTE_PERSONS));
//    }
//}
