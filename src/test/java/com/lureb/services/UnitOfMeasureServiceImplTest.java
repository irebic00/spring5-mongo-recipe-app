package com.lureb.services;

import com.lureb.commands.UnitOfMeasureCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.model.UnitOfMeasure;
import com.lureb.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UnitOfMeasureServiceImplTest {
    ModelConverter modelConverter = new ModelConverter();
    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, modelConverter);
    }

    @Test
    public void listAllUoms() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("id1");
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("id2");
        unitOfMeasures.add(uom2);

        Mockito.when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        //when
        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        //then
        assertEquals(2, commands.size());
        Mockito.verify(unitOfMeasureRepository, Mockito.times(1)).findAll();
    }
}