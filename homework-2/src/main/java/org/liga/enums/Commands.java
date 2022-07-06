package org.liga.enums;

import org.liga.exception.WrongCommandParametersException;
import org.liga.mapper.TaskMapper;
import org.liga.mapper.UserMapper;
import org.liga.model.Task;
import org.liga.model.User;
import org.liga.service.TaskService;
import org.liga.service.UserService;
import org.liga.util.Constants;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public enum Commands {

    ALL_USERS {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            StringBuilder response = new StringBuilder();
            if (parameters.size() == 1) {
                List<User> users = userService.findAll();
                for (User user : users) {
                    response.append(user.toString()).append(System.lineSeparator());
                }
            } else {
                throw new WrongCommandParametersException();
            }
            return response.toString();
        }
    },

    ALL_TASKS {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            StringBuilder response = new StringBuilder();
            int size = parameters.size();
            List<Task> tasks;
            if (size == 1) {
                tasks = taskService.findAll();
            } else if (size == 2) {
                String status = parameters.get(1).trim();
                tasks = taskService.findAllByStatus(Status.valueOf(status));
            } else {
                throw new WrongCommandParametersException();
            }
            for (Task task: tasks) {
                Integer userId = task.getUserId();
                User user = userService.findById(userId).orElse(User.builder().build());
                response.append(user).append(" - ").append(task).append(System.lineSeparator());
            }
            return response.toString();
        }
    },

    NEW_USER {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            int size = parameters.size();
            String response = "";
            if (size == 3) {
                String parametersLine = parameters.stream()
                        .skip(1)
                        .collect(Collectors.joining(","));
                Optional<User> user = userService.create(parametersLine);
                response = "Пользователь добавлен: " + user.orElseThrow(WrongCommandParametersException::new);
            }
            return response;
        }
    },

    NEW_TASK {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            int size = parameters.size();
            String response = "";
            if ((size > 4) && (size < 7)) {
                String parametersLine = parameters.stream()
                        .skip(1)
                        .collect(Collectors.joining(","));
                Optional<Task> task = taskService.create(parametersLine);
                response = "Задача добавлена: " + task.orElseThrow(WrongCommandParametersException::new);
            }
            return response;
        }
    },

    GET_USER {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            if (parameters.size() != 2) {
                throw new WrongCommandParametersException();
            }
            int id = Integer.parseInt(parameters.get(1).trim());
            User founded = userService.findById(id).orElseThrow(EntityNotFoundException::new);
            return "UPDATE_USER, " + UserMapper.userToString(founded);
        }
    },

    GET_TASK {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            if (parameters.size() != 2) {
                throw new WrongCommandParametersException();
            }
            int id = Integer.parseInt(parameters.get(1).trim());
            Task founded = taskService.findById(id).orElseThrow(EntityNotFoundException::new);
            return "UPDATE_TASK, " + TaskMapper.taskToString(founded);
        }
    },

    UPDATE_USER {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            int size = parameters.size();
            String response = "";
            if (size == 4) {
                int id = Integer.parseInt(parameters.get(1).trim());
                String parametersLine = parameters.stream()
                        .skip(2)
                        .collect(Collectors.joining(","));
                Optional<User> user = userService.update(id, parametersLine);
                response = "Пользователь обновлен: " + user.orElseThrow(WrongCommandParametersException::new);
            }
            return response;
        }
    },

    UPDATE_TASK {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            int size = parameters.size();
            String response = "";
            if ((size > 4) && (size < 7)) {
                int id = Integer.parseInt(parameters.get(1).trim());
                String parametersLine = parameters.stream()
                        .skip(2)
                        .collect(Collectors.joining(","));
                Optional<Task> task = taskService.update(id, parametersLine);
                response = "Задача обновлена: " + task.orElseThrow(WrongCommandParametersException::new);
            }
            return response;
        }
    },

    DELETE_ALL_USERS {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            if (parameters.size() == 1) {
                userService.deleteAll();
            } else {
                throw new WrongCommandParametersException();
            }
            return "Все пользователи удалены";
        }
    },

    DELETE_ALL_TASKS {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            if (parameters.size() == 1) {
                taskService.deleteAll();
            } else {
                throw new WrongCommandParametersException();
            }
            return "Все задачи удалены";
        }
    },

    DELETE_USER {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            String response;
            if (parameters.size() == 2) {
                int id = Integer.parseInt(parameters.get(1).trim());
                userService.deleteById(id);
                response = "Пользователь с id = " + id + " удален";
            } else {
                throw new WrongCommandParametersException();
            }
            return response;
        }
    },

    DELETE_TASK {
        String response = "";
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            if (parameters.size() == 2) {
                int id = Integer.parseInt(parameters.get(1).trim());
                taskService.deleteById(id);
                response = "Задача с id = " + id + " удалена";
            } else {
                throw new WrongCommandParametersException();
            }
            return response;
        }
    },

    CHANGE_STATUS {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            int size = parameters.size();
            String response;
            if (size == 3) {
                int id = Integer.parseInt(parameters.get(1).trim());
                String status = parameters.get(2).trim();
                taskService.changeStatus(id, status);
                response = "Статус обновлен";
            } else {
                throw new WrongCommandParametersException();
            }
            return response;
        }
    },
    HELP {
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            return Constants.HELP_TEXT;
        }
    },
    EXIT{
        @Override
        public String action(UserService userService, TaskService taskService, List<String> parameters) {
            return Constants.GOODBYE_MESSAGE;
        }
    };

    public abstract String action(UserService userService, TaskService taskService, List<String> parameters);
}
