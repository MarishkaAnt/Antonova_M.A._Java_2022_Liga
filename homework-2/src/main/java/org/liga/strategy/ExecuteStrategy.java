package org.liga.strategy;

import org.liga.model.AbstractEntity;
import org.liga.service.AbstractService;

import java.util.List;

@FunctionalInterface
public interface ExecuteStrategy <T extends AbstractService<? extends AbstractEntity, Integer>>{
    String execute(T service, List<String> parameters);
}
