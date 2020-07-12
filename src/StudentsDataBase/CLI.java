package StudentsDataBase;


import StudentsDataBase.impl.ClassBookDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(CLI.class);
    private static final List cmds = Collections.unmodifiableList(Arrays.asList(
            "/addStudent",
            "/getStudent",
            "/removeStudent",
            "/transferToAnotherClass"
    ));
    private static final List cmdsWithDescription = Collections.unmodifiableList(Arrays.asList(
            "/addStudent [name] [surname] [age] [number] [class]",
            "/getStudent [surname]",
            "/removeStudent [surname]",
            "/transferToAnotherClass [age] [class]"));


    public static void main(String[] args) {
        try (Connection c = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD)) {
            try (Statement st = c.createStatement()) {
                System.out.println("Журнал начал свою работу.\nДля просмотра доступных команд введите /help");
                waitCommand(st);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static void waitCommand(Statement st) throws SQLException{
        ClassBook cb = new ClassBookDefault();
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name())) {
            while (true) {
                String cmd = scanner.nextLine();
                Optional<String> checkCmd = Optional.ofNullable(cmd);
                if (checkCmd.isPresent()) {
                    String[] fullCmd = checkCmd.get().split(" ");
                    //addStudent
                    if (fullCmd[0].equalsIgnoreCase(cmds.get(0).toString())) {
                        if (fullCmd.length == 7) {
                            cb.addStudent(st,
                                    toCorrectCase(fullCmd[1]),
                                    toCorrectCase(fullCmd[2]),
                                    Integer.parseInt(fullCmd[3]),
                                    toCorrectNumber(fullCmd[4]),
                                    Integer.parseInt(fullCmd[5]),
                                    toCorrectClass(fullCmd[6]));
                        } else {
                            LOGGER.error("Вы указали {}/6 параметров.", fullCmd.length - 1);
                        }
                        //getStudent
                    } else if (fullCmd[0].equalsIgnoreCase(cmds.get(1).toString())) {
                        if (fullCmd.length == 2) cb.getStudent(st, toCorrectCase(fullCmd[1]));
                        else LOGGER.error("Вы указали {}/1 параметров", fullCmd.length - 1);
                        //removeStudent
                    } else if (fullCmd[0].equalsIgnoreCase(cmds.get(2).toString())) {
                        if (fullCmd.length == 2) cb.removeStudent(st, toCorrectCase(fullCmd[1]));
                        else LOGGER.error("Вы указали {}/1 параметров", fullCmd.length - 1);
                        //transferToAnotherClass
                    } else if (fullCmd[0].equalsIgnoreCase(cmds.get(3).toString())) {
                        if (fullCmd.length == 3) cb.transferToAnotherClass(st, Integer.parseInt(fullCmd[1]), Integer.parseInt(fullCmd[2]));
                        else LOGGER.error("Вы указали {}/2 параметров", fullCmd.length - 1);
                    } else if (fullCmd[0].equalsIgnoreCase("/help")) {
                        cmdsWithDescription.forEach(System.out::println);
                    } else {
                        LOGGER.error("Команды {} не существует. Что бы посмотреть список поддерживаемых команд введите /help", fullCmd[0]);


                    }

                }
            }
        }
    }

    private static String toCorrectNumber(String number) {
        long countNumber = number.chars().count();
        if (countNumber == 9) {
            return "380" + number;
        } else if (countNumber == 12 && number.substring(0, 3) != "380") {
            return "380" + number.substring(3, 12);
        } else {
            return number;
        }
    }

    private static String toCorrectCase(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    private static String toCorrectClass(String clas) {
        if (clas.equalsIgnoreCase("A")) return "A";
        if (clas.equalsIgnoreCase("B")) return "B";
        else return null;

    }
}

