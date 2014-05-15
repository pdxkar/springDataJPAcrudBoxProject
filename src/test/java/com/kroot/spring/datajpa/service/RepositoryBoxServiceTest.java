package com.kroot.spring.datajpa.service;

import com.kroot.spring.datajpa.dto.BoxDTO;
import com.kroot.spring.datajpa.model.Box;
import com.kroot.spring.datajpa.model.PersonTestUtil;
import com.kroot.spring.datajpa.repository.PersonRepository;
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
    
    private RepositoryPersonService personService;

    private PersonRepository personRepositoryMock;

    @Before
    public void setUp() {
        personService = new RepositoryPersonService();

        personRepositoryMock = mock(PersonRepository.class);
        personService.setPersonRepository(personRepositoryMock);
    }
    
    @Test
    public void create() {
        BoxDTO created = PersonTestUtil.createDTO(null, FIRST_NAME, LAST_NAME);
        Box persisted = PersonTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        
        when(personRepositoryMock.save(any(Box.class))).thenReturn(persisted);
        
        Box returned = personService.create(created);

        ArgumentCaptor<Box> personArgument = ArgumentCaptor.forClass(Box.class);
        verify(personRepositoryMock, times(1)).save(personArgument.capture());
        verifyNoMoreInteractions(personRepositoryMock);

        assertPerson(created, personArgument.getValue());
        assertEquals(persisted, returned);
    }
    
    @Test
    public void delete() throws PersonNotFoundException {
        Box deleted = PersonTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        when(personRepositoryMock.findOne(PERSON_ID)).thenReturn(deleted);
        
        Box returned = personService.delete(PERSON_ID);
        
        verify(personRepositoryMock, times(1)).findOne(PERSON_ID);
        verify(personRepositoryMock, times(1)).delete(deleted);
        verifyNoMoreInteractions(personRepositoryMock);
        
        assertEquals(deleted, returned);
    }
    
    @Test(expected = PersonNotFoundException.class)
    public void deleteWhenPersonIsNotFound() throws PersonNotFoundException {
        when(personRepositoryMock.findOne(PERSON_ID)).thenReturn(null);
        
        personService.delete(PERSON_ID);
        
        verify(personRepositoryMock, times(1)).findOne(PERSON_ID);
        verifyNoMoreInteractions(personRepositoryMock);
    }
    
    @Test
    public void findAll() {
        List<Box> boxes = new ArrayList<Box>();
        when(personRepositoryMock.findAll()).thenReturn(boxes);
        
        List<Box> returned = personService.findAll();
        
        verify(personRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(personRepositoryMock);
        
        assertEquals(boxes, returned);
    }
    
    @Test
    public void findById() {
        Box box = PersonTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        when(personRepositoryMock.findOne(PERSON_ID)).thenReturn(box);
        
        Box returned = personService.findById(PERSON_ID);
        
        verify(personRepositoryMock, times(1)).findOne(PERSON_ID);
        verifyNoMoreInteractions(personRepositoryMock);
        
        assertEquals(box, returned);
    }
    
    @Test
    public void update() throws PersonNotFoundException {
        BoxDTO updated = PersonTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
        Box box = PersonTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        
        when(personRepositoryMock.findOne(updated.getId())).thenReturn(box);
        
        Box returned = personService.update(updated);
        
        verify(personRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(personRepositoryMock);
        
        assertPerson(updated, returned);
    }
    
    @Test(expected = PersonNotFoundException.class)
    public void updateWhenPersonIsNotFound() throws PersonNotFoundException {
        BoxDTO updated = PersonTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
        
        when(personRepositoryMock.findOne(updated.getId())).thenReturn(null);

        personService.update(updated);

        verify(personRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(personRepositoryMock);
    }

    private void assertPerson(BoxDTO expected, Box actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), expected.getLastName());
    }

}
