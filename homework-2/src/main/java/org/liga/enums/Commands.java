package org.liga.enums;

import org.liga.dao.TaskDAO;
import org.liga.dao.UserDAO;
import org.liga.exception.WrongCommandParameters;

import java.util.List;

public enum Commands {

    ALL_USERS {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {
            if(parameters.size() == 1) {
                userDAO.findAll()
                        .forEach(System.out::println);
            } else {
                throw new WrongCommandParameters();
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
                throw new WrongCommandParameters();
            }
        }
    },

    NEW_TASK {
        @Override
        public void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters) {

        }
    };

    public abstract void action(UserDAO userDAO, TaskDAO taskDAO, List<String> parameters);
}
