package org.liga.strategy;

import org.liga.model.Identifiable;
import org.liga.service.AbstractService;

import java.util.List;

@FunctionalInterface
public interface ExecuteStrategy <T extends AbstractService<? extends Identifiable, Integer>>{
    String execute(T service, List<String> parameters);
}
