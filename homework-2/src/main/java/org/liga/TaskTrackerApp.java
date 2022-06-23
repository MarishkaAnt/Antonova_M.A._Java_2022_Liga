package org.liga;

import org.liga.service.UserCommandHandler;

import java.util.Scanner;

public class TaskTrackerApp {
    private static final String HELP = """
            Привет! Таск-трекер готов к работе.
            Работа с приложением осуществляется через консоль.
            ВНИМАНИЕ! используйте запятые только для разделения параметров запроса.
            * Для вывода всех имеющихся заданий введите:
                ALL_TASKS
            * Для создания нового задания введите:
                NEW_TASK, [название задания], [описание задания], [ид пользователя], [дата окончания]
                Например: NEW_TASK, новое задание, создать новое задание, 1, 22.06.2022
            * Для изменения статуса задачи введите:
                CHANGE_STATUS, [номер задания], [новый статус]
                Статус может быть: новое / в работе / готово
                При создании задания присваивается статус "новое" по умолчанию.
            * Для вывода всех пользователей, офильтрованных по статусу задания введите:
                FILTER, [статус задания]
                Например: FILTER, новое
                Пример вывода:
                    1. Иван Иванов - 1. Решить задачу: Решить поставленную задачу (новое) - 22.06.2022
                    1. Иван Иванов - 2. Создать задание: Создать новое задание (новое) - 22.06.2022
                    2. Пётр Петров - 3. Выполнить ДЗ: Придумать и написать кучу кода (новое) - 23.06.2022
            * Для вывода всех имеющихся пользователей введите:
                ALL_USERS
            * Для добавления нового пользователя введите: NEW_USER, [имя], [фамилия]
                        
            Введите команду или введите "EXIT" для завершения программы:
            """;

    static {
        System.out.println(HELP);
    }

    public static void main(String[] args) {
        UserCommandHandler userCommandHandler = UserCommandHandler.getInstance();
        Scanner sc = new Scanner(System.in);
        boolean isAppClosingNeed = false;
        do {
            String command = sc.next();
            if (command.equalsIgnoreCase("EXIT")) {
                isAppClosingNeed = true;
            } else {
                userCommandHandler.handleCommand(command);
                System.out.println("Введите команду или введите \"EXIT\" для завершения программы:");
            }
        }
        while (!isAppClosingNeed && sc.hasNext());

        System.out.println("""
                До свидания, надеюсь, приложение было полезно!
                Все вопросы и пожелания можно направить сюда: marishka.ant@gmail.com
                """);
    }
}
