package com.lureb.services;

import com.lureb.commands.UnitOfMeasureCommand;
import com.lureb.converter.ModelConverter;
import com.lureb.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final ModelConverter modelConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, ModelConverter modelConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport
                .stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasure -> modelConverter.convertValue(unitOfMeasure, UnitOfMeasureCommand.class))
                .collect(Collectors.toSet());
    }
}
