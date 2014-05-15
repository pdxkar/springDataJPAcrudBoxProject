package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Box;
import com.kroot.spring.datajpa.model.BoxTestUtil;
import com.kroot.spring.datajpa.repository.BoxRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RepositoryBoxServiceTest {

    private static final Long PERSON_ID = Long.valueOf(5);
    private static final String FIRST_NAME = "Foo";
    private static final String FIRST_NAME_UPDATED = "FooUpdated";
    private static final String LAST_NAME = "Bar";
    private static final String LAST_NAME_UPDATED = "BarUpdated";

    private RepositoryBoxService personService;

    private BoxRepository boxRepositoryMock;

    @Before
    public void setUp() {
        personService = new RepositoryBoxService();

        boxRepositoryMock = mock(BoxRepository.class);
        personService.setBoxRepository(boxRepositoryMock);
    }

    @Test
    public void create() {
        BoxDTO created = BoxTestUtil.createDTO(null, FIRST_NAME, LAST_NAME);
        Box persisted = BoxTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);

        when(boxRepositoryMock.save(any(Box.class))).thenReturn(persisted);

        Box returned = personService.create(created);

        ArgumentCaptor<Box> personArgument = ArgumentCaptor.forClass(Box.class);
        verify(boxRepositoryMock, times(1)).save(personArgument.capture());
        verifyNoMoreInteractions(boxRepositoryMock);

        assertPerson(created, personArgument.getValue());
        assertEquals(persisted, returned);
    }

    @Test
    public void delete() throws BoxNotFoundException {
        Box deleted = BoxTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        when(boxRepositoryMock.findOne(PERSON_ID)).thenReturn(deleted);

        Box returned = personService.delete(PERSON_ID);

        verify(boxRepositoryMock, times(1)).findOne(PERSON_ID);
        verify(boxRepositoryMock, times(1)).delete(deleted);
        verifyNoMoreInteractions(boxRepositoryMock);

        assertEquals(deleted, returned);
    }

    @Test(expected = BoxNotFoundException.class)
    public void deleteWhenPersonIsNotFound() throws BoxNotFoundException {
        when(boxRepositoryMock.findOne(PERSON_ID)).thenReturn(null);

        personService.delete(PERSON_ID);

        verify(boxRepositoryMock, times(1)).findOne(PERSON_ID);
        verifyNoMoreInteractions(boxRepositoryMock);
    }

    @Test
    public void findAll() {
        List<Box> boxes = new ArrayList<Box>();
        when(boxRepositoryMock.findAll()).thenReturn(boxes);

        List<Box> returned = personService.findAll();

        verify(boxRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(boxRepositoryMock);

        assertEquals(boxes, returned);
    }

    @Test
    public void findById() {
        Box box = BoxTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        when(boxRepositoryMock.findOne(PERSON_ID)).thenReturn(box);

        Box returned = personService.findById(PERSON_ID);

        verify(boxRepositoryMock, times(1)).findOne(PERSON_ID);
        verifyNoMoreInteractions(boxRepositoryMock);

        assertEquals(box, returned);
    }

    @Test
    public void update() throws BoxNotFoundException {
        BoxDTO updated = BoxTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
        Box box = BoxTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);

        when(boxRepositoryMock.findOne(updated.getId())).thenReturn(box);

        Box returned = personService.update(updated);

        verify(boxRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(boxRepositoryMock);

        assertPerson(updated, returned);
    }

    @Test(expected = BoxNotFoundException.class)
    public void updateWhenPersonIsNotFound() throws BoxNotFoundException {
        BoxDTO updated = BoxTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);

        when(boxRepositoryMock.findOne(updated.getId())).thenReturn(null);

        personService.update(updated);

        verify(boxRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(boxRepositoryMock);
    }

    private void assertPerson(BoxDTO expected, Box actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), expected.getLastName());
    }

}
