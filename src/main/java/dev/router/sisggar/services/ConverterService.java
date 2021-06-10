package dev.router.sisggar.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import dev.router.sisggar.config.WebConfig;

/**
 * A class that converts from a type to another one
 * 
 * @param TEntity the database entity model
 * @param TDto    the dto model
 */

public class ConverterService<TEntity, TDto> {

    /**
     * The default constructor
     * 
     * @param entityClass the class of the entity model
     * @param dtoClass    the class of the dto model
     */
    public ConverterService(Class<TEntity> entityClass, Class<TDto> dtoClass) {
        // Assignin the values
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;

        modelMapper = new WebConfig().modelMapper();
    }

    /**
     * Store the mapping configuration to performs object mapping, maintains
     * Configuration and stores
     */

    private ModelMapper modelMapper;

    /**
     * Store the dto class value
     */
    private Class<TDto> dtoClass;

    /**
     * Store the entity class value
     */
    private Class<TEntity> entityClass;

    // Simple Object Conversion
    
    /**
     * Maps a model from Entity to DTO
     * @param model the model to be mapped
     * @return the DTO of the Entity
     */
    public TDto toDto(TEntity model) {
        // Mapping and retung the value mapped
        return modelMapper.map(model, dtoClass);
    }

    /**
     * Maps a model from DTO to Entity
     * @param model the model to be mapped
     * @return the Entity of the DTO
     */
    public TEntity toEntity(TDto model) {
        // Mapping and retung the value mapped
        return modelMapper.map(model, entityClass);
    }

    // Array Object Conversion

    /**
     * Maps a list of models from DTOs to Entities
     * @param model the list of models to be mapped
     * @return the Entities of the DTOs
     */
    public List<TDto> toDto(List<TEntity> model) {
        return model.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Maps a list of models from Entities to DTOs
     * @param model the list of models to be mapped
     * @return the of DTOs the Entities
     */
    public List<TEntity> toEntity(List<TDto> model) {
        return model.stream().map(this::toEntity).collect(Collectors.toList());
    }

    // Converter With modifier

    // Simple Object Conversion
    
    /**
     * Maps a model from Entity to DTO
     * @param model thegit  model to be mapped
     * @param modifier the delegate modifier that allows to modify properties to the result object 
     * @return the DTO of the Entity
     */
    public TDto toDto(TEntity model, IModifierDelegate<TEntity, TDto> modifier) {
        // Mapping and retung the value mapped
        TDto dto = modelMapper.map(model, dtoClass);

        // Checking if the modifier is null
        if (modifier == null) return dto;

        // Applying the changes provided
        modifier.applyChanges(model, dto);

        return dto;
    }

    /**
     * Maps a model from DTO to Entity
     * @param model the model to be mapped
     * @param modifier the delegate modifier that allows to modify properties to the result object 
     * @return the Entity of the DTO
     */
    public TEntity toEntity(TDto model, IModifierDelegate<TEntity, TDto> modifier) {
        // Mapping and retung the value mapped
        TEntity entity = modelMapper.map(model, entityClass);

        // Checking if the modifier is null
        if (modifier == null) return entity;

        // Applying the changes provided
        modifier.applyChanges(entity, model);

        return entity;
    }

    // Array Object Conversion

    /**
     * Maps a list of models from DTOs to Entities
     * @param model the list of models to be mapped
     * @param modifier the delegate modifier that allows to modify properties to the result object 
     * @return the Entities of the DTOs
     */
    public List<TDto> toDto(List<TEntity> model, IModifierDelegate<TEntity, TDto> modifier) {
        return model.stream().map((item) -> this.toDto(item, modifier)).collect(Collectors.toList());
    }

    /**
     * Maps a list of models from Entities to DTOs
     * @param model the list of models to be mapped
     * @param modifier the delegate modifier that allows to modify properties to the result object 
     * @return the of DTOs the Entities
     */
    public List<TEntity> toEntity(List<TDto> model, IModifierDelegate<TEntity, TDto> modifier) {
        return model.stream().map((item) -> this.toEntity(item, modifier)).collect(Collectors.toList());
    }
}