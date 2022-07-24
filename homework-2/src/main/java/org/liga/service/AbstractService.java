package org.liga.service;

import org.liga.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T extends AbstractEntity, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    Optional<T> create(T t);

    void deleteAll();

    void deleteById(ID id);

    Optional<T> update(ID id, T t);


}
