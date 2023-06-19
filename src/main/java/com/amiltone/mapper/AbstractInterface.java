package com.amiltone.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface AbstractInterface<T, D> {

    List<D> toDomainList(List<T> entities);

    D toDomain(T entity);

    @InheritInverseConfiguration(name = "toDomain")
    T toEntity(D domain);

    void updateEntityFromDomain(D domain, @MappingTarget T entity);

    void updateDomainFromEntity(T entity, @MappingTarget D domain);

}
