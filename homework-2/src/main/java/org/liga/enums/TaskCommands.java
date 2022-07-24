package org.liga.enums;

import org.liga.exception.WrongCommandParametersException;
import org.liga.mapper.TaskMapper;
import org.liga.model.Task;
import org.liga.model.User;
import org.liga.service.TaskService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum TaskCommands {

    ALL_TASKS {
        @Override
        public String action(TaskService taskService, List<String> parameters) {
            int requiredAmountOfParamsToFindAll = 1;
            int requiredAmountOfParamsToFindAllByStatus = 2;
            StringBuilder response = new StringBuilder();
            int size = parameters.size();
            List<Task> tasks;
            if (size == requiredAmountOfParamsToFindAll) {
                tasks = taskService.findAll();
            } else if (size == requiredAmountOfParamsToFindAllByStatus) {
                String status = parameters.get(1).trim();
                tasks = taskService.findAllByStatus(Status.valueOf(status));
            } else {
                throw new WrongCommandParametersException();
            }
            for (Task task : tasks) {
                User user = task.getUser();
                response.append(user).append(" - ").append(task).append(System.lineSeparator());
            }
            return response.toString();
        }
    },

    NEW_TASK {
        @Override
        public String action(TaskService taskService, List<String> parameters) {
            int requiredAmountOfParamsWithoutStatus = 5;
            int requiredAmountOfParamsWithStatus = 6;
            int size = parameters.size();
            String response;
            if (size == requiredAmountOfParamsWithoutStatus || size == requiredAmountOfParamsWithStatus) {
                String parametersLine = parameters.stream()
                        .skip(1)
                        .collect(Collectors.joining(","));
                Task taskFromString = TaskMapper.stringToTask(parametersLine);
                Optional<Task> task = taskService.create(taskFromString);
                response = "Задача добавлена: " + task.orElseThrow(WrongCommandParametersException::new);
            } else {
                throw new WrongCommandParametersException();
            }
            return response;
        }
    },

    GET_TASK {
        @Override
        public String action(TaskService taskService, List<String> parameters) {
            int requiredAmountOfParams = 2;
            if (parameters.size() != requiredAmountOfParams) {
                throw new WrongCommandParametersException();
            }
            int id = Integer.parseInt(parameters.get(1).trim());
            Task founded = taskService.findById(id).orElseThrow(EntityNotFoundException::new);
            return "UPDATE_TASK, " + TaskMapper.taskToString(founded);
        }
    },

    UPDATE_TASK {
        @Override
        public String action(TaskService taskService, List<String> parameters) {
            int requiredAmountOfParams = 6;
            int size = parameters.size();
            String response;
            if (size == requiredAmountOfParams) {
                int id = Integer.parseInt(parameters.get(1).trim());
                String parametersLine = parameters.stream()
                        .skip(2)
                        .collect(Collectors.joining(","));
                Task newTask = TaskMapper.stringToTask(parametersLine);
                Optional<Task> task = taskService.update(id, newTask);
                response = "Задача обновлена: " + task.orElseThrow(WrongCommandParametersException::new);
            } else {
                throw new WrongCommandParametersException();
            }
            return response;
        }
    },

    DELETE_ALL_TASKS {
        @Override
        public String action(TaskService taskService, List<String> parameters) {
            int requiredAmountOfParams = 1;
            if (parameters.size() == requiredAmountOfParams) {
                taskService.deleteAll();
            } else {
                throw new WrongCommandParametersException();
            }
            return "Все задачи удалены";
        }
    },

    DELETE_TASK {

        @Override
        public String action(TaskService taskService, List<String> parameters) {
            String response;
            int requiredAmountOfParams = 2;
            if (parameters.size() == requiredAmountOfParams) {
                int id = Integer.parseInt(parameters.get(1).trim());
                taskService.deleteById(id);
                response = "Задача с id = " + id + " удалена";
            } else {
                throw new WrongCommandParametersException();
            }
            return response;
        }
    },

    CHANGE_TASK_STATUS {
        @Override
        public String action(TaskService taskService, List<String> parameters) {
            int requiredAmountOfParams = 3;
            int size = parameters.size();
            String response;
            if (size == requiredAmountOfParams) {
                int id = Integer.parseInt(parameters.get(1).trim());
                String status = parameters.get(2).trim();
                taskService.changeStatus(id, Status.valueOf(status));
                response = "Статус обновлен";
            } else {
                throw new WrongCommandParametersException();
            }
            return response;
        }
    };

    public abstract String action(TaskService service, List<String> parameters);
}