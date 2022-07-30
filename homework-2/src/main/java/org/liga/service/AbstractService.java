package org.liga.service;

import org.liga.model.Identifiable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface AbstractService<T extends Identifiable, ID> {

    List<T> findAll();

    Optional<T> findById(@NotNull ID id);

    Optional<T> create(@Valid @NotNull T t);

    void deleteAll();

    void deleteById(@NotNull ID id);

    Optional<T> update(@NotNull ID id, @Valid @NotNull T t);

}
