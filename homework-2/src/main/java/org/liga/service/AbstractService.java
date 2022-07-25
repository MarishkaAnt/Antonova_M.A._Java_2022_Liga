package org.liga.service;

import org.liga.model.AbstractEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Validated
public interface AbstractService<T extends AbstractEntity, ID> {

    List<T> findAll();

    Optional<T> findById(@NotNull @Positive ID id);

    Optional<T> create(@Valid @NotNull T t);

    void deleteAll();

    void deleteById(@NotNull @Positive ID id);

    Optional<T> update(@NotNull @Positive ID id,@Valid @NotNull T t);


}
