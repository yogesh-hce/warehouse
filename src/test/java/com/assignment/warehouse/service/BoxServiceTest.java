package com.assignment.warehouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.assignment.warehouse.entity.Box;
import com.assignment.warehouse.repository.BoxRepository;

@ExtendWith(MockitoExtension.class)
public class BoxServiceTest {

    @Mock
    private BoxRepository boxRepository;

    @InjectMocks
    private BoxService boxService;

    @Test
    public void testCreateBox() {
        Box box = new Box(10, "Aisle 2");
        Mockito.when(boxRepository.save(box)).thenReturn(box);

        Box createdBox = boxService.createBox(box);

        assertEquals(box, createdBox);
        Mockito.verify(boxRepository).save(box);
    }

    @Test
    public void testGetBoxById_Found() {
        Long boxId = 1L;
        Box expectedBox = new Box(20, "Shelf C");
        Mockito.when(boxRepository.findById(boxId)).thenReturn(Optional.of(expectedBox));

        Box retrievedBox = boxService.getBoxById(boxId);

        assertEquals(expectedBox, retrievedBox);
        Mockito.verify(boxRepository).findById(boxId);
    }

    @Test
    public void testGetBoxById_NotFound() {
        Long boxId = 1L;
        Mockito.when(boxRepository.findById(boxId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> boxService.getBoxById(boxId));
    }
}
