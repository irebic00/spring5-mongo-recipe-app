package com.lureb.services;

import com.lureb.commands.UnitOfMeasureCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final ModelConverter modelConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
                                    ModelConverter modelConverter) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {
        return unitOfMeasureReactiveRepository
                .findAll()
                .map(unitOfMeasure -> modelConverter.convertValue(unitOfMeasure, UnitOfMeasureCommand.class));
    }
}
