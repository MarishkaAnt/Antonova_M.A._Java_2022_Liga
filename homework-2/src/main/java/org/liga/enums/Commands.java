package org.liga.enums;

import org.liga.dao.TaskDAO;
import org.liga.dao.UserDAO;
import org.liga.exception.WrongCommandParametersException;
import org.liga.mapper.TaskMapper;
import org.liga.mapper.UserMapper;
import org.liga.model.Task;
import org.liga.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Commands {

    ALL_USERS {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            if (parameters.size() == 1) {
                userDAO.findAll()
                        .forEach(System.out::println);
            } else {
                throw new WrongCommandParametersException();
            }
        }
    },

    ALL_TASKS {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            int size = parameters.size();
            if (size == 1) {
                taskDAO.findAll().forEach(System.out::println);
            } else if (size == 2) {
                String status = parameters.get(1);
                taskDAO.findAllByStatus(status).stream()
                        .peek(t -> {
                            Integer userId = t.getUserId();
                            System.out.print(userDAO.findById(userId) + " ");
                        })
                        .forEach(System.out::println);
            } else {
                throw new WrongCommandParametersException();
            }
        }
    },

    NEW_USER {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            int size = parameters.size();
            Boolean created = false;
            if (size == 4) {
                String parametersLine = parameters.stream()
                        .skip(1)
                        .collect(Collectors.joining(","));
                created = userDAO.create(parametersLine);
                System.out.println("Пользователь добавлен, проверить можно введя команду ALL_USERS");
            }
            if (!created) {
                throw new WrongCommandParametersException();
            }
        }
    },

    NEW_TASK {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            int size = parameters.size();
            Boolean created = false;
            if ((size > 4) && (size < 7)) {
                String parametersLine = parameters.stream()
                        .skip(1)
                        .collect(Collectors.joining(","));
                created = taskDAO.create(parametersLine);
                System.out.println("Задача добавлена, проверить можно введя команду ALL_TASKS");
            }
            if (!created) {
                throw new WrongCommandParametersException();
            }
        }
    },

    GET_USER {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            if (parameters.size() != 2) {
                throw new WrongCommandParametersException();
            }
            int id = Integer.parseInt(parameters.get(1).trim());
            Optional<User> founded = userDAO.findById(id);
            founded.ifPresent(user ->
                    System.out.println("UPDATE_USER, " +
                            UserMapper.userToString(user)));

        }
    },

    GET_TASK {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            if (parameters.size() != 2) {
                throw new WrongCommandParametersException();
            }
            int id = Integer.parseInt(parameters.get(1).trim());
            Optional<Task> founded = taskDAO.findById(id);
            founded.ifPresent(task ->
                    System.out.println("UPDATE_TASK, " +
                            TaskMapper.taskToString(task)));
        }
    },

    UPDATE_USER {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            int size = parameters.size();
            if (size == 4) {
                String parametersLine = parameters.stream()
                        .skip(1)
                        .collect(Collectors.joining(","));
                userDAO.update(UserMapper.stringToUser(parametersLine));
                System.out.println("Пользователь обновлен, проверить можно введя команду ALL_USERS");
            } else {
                throw new WrongCommandParametersException();
            }
        }
    },

    UPDATE_TASK {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            int size = parameters.size();
            if ((size > 4) && (size < 7)) {
                String parametersLine = parameters.stream()
                        .skip(1)
                        .collect(Collectors.joining(","));
                taskDAO.update(TaskMapper.stringToTask(parametersLine));
                System.out.println("Задача обновлена, проверить можно введя команду ALL_TASKS");
            } else {
                throw new WrongCommandParametersException();
            }

        }
    },

    DELETE_ALL_USERS {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            if (parameters.size() == 1) {
                userDAO.deleteAll();
            } else {
                throw new WrongCommandParametersException();
            }
        }
    },

    DELETE_ALL_TASKS {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            if (parameters.size() == 1) {
                taskDAO.deleteAll();
            } else {
                throw new WrongCommandParametersException();
            }

        }
    },

    DELETE_USER {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            if (parameters.size() == 2) {
                int id = Integer.parseInt(parameters.get(1));
                userDAO.deleteById(id);
            } else {
                throw new WrongCommandParametersException();
            }
        }
    },

    DELETE_TASK {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            if (parameters.size() == 2) {
                int id = Integer.parseInt(parameters.get(1));
                taskDAO.deleteById(id);
            } else {
                throw new WrongCommandParametersException();
            }
        }
    },

    CHANGE_STATUS {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            int size = parameters.size();
            if (size == 3) {
                int id = Integer.parseInt(parameters.get(1).trim());
                String status = parameters.get(2).trim();
                taskDAO.changeStatus(id, status);
            } else {
                throw new WrongCommandParametersException();
            }
        }
    };

    public abstract void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters);
}
