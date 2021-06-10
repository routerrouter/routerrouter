package dev.router.sisggar.services;

@FunctionalInterface
public interface IModifierDelegate<TEntity, TDto> {
    void applyChanges(TEntity entity, TDto dto);
}