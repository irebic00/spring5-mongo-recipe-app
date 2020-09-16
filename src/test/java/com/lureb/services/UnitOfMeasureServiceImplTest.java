package com.lureb.services;

import com.lureb.commands.UnitOfMeasureCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.model.UnitOfMeasure;
import com.lureb.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UnitOfMeasureServiceImplTest {
    ModelConverter modelConverter = new ModelConverter();
    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, modelConverter);
    }

    @Test
    public void listAllUoms() {
        //given
        ObjectId id1 = new ObjectId();
        ObjectId id2 = new ObjectId();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(id1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(id2);

        Mockito.when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

        //when
        List<UnitOfMeasureCommand> commands = service.listAllUoms().collectList().block();

        //then
        assertEquals(2, commands.size());
        Mockito.verify(unitOfMeasureReactiveRepository, Mockito.times(1)).findAll();
    }
}