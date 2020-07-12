package StudentsDataBase.impl;

import StudentsDataBase.ClassBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassBookDefault implements ClassBook {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassBookDefault.class);

    @Override
    public void addStudent(Statement st, String firstName, String lastName, int age, String number, int clas,String subclass) throws SQLException {
        try {
            String insertTableSQL = "INSERT INTO STUDENTS"
                    + "(FIRST_NAME, LAST_NAME, AGE, NUMBER, CLASS, SUBCLASS) " + "VALUES"
                    + "('" + firstName + "','" + lastName + "'," + age + ",'" + number + "'," + clas + ",'" + subclass + "')";

            if (subclass == null) {
                LOGGER.error("Вероятнее всего вы указали не существующий класс. Пожалуйста,укажите другой. Существующие классы: A,B");
            } else {
                st.executeUpdate(insertTableSQL);
                LOGGER.info("В журнал добавлен {} {}", firstName, lastName);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void removeStudent(Statement st, String lastName) throws SQLException {
        int delete = st.executeUpdate("DELETE FROM STUDENTS WHERE LAST_NAME = '" + lastName + "'");
        if (delete == 0) {
            LOGGER.info("Ученика с фамилией {} не существует", lastName);
        } else {
            LOGGER.info("Операция прошла успешно.");
        }

    }


    @Override
    public void getStudent(Statement st, String lastName) throws SQLException {
        try (ResultSet rs = st.executeQuery("SELECT * FROM STUDENTS WHERE LAST_NAME = '" + lastName + "'")) {
            boolean isExists = false;
            while (rs.next()) {
              LOGGER.info("\nИмя: {}\nФамилия: {}\nВозраст: {}\nНомер: {}\nКласс {}-{}",
                      rs.getString(1),
                      rs.getString(2),
                      rs.getInt(3),
                      rs.getString(4),
                      rs.getInt(5),
                      rs.getString(6));
                isExists = true;
            }
            if (!isExists) {
                LOGGER.info("Ученик с фамилией {} не существует", lastName);
            }
        }

    }

    @Override
    public void transferToAnotherClass(Statement st, int age,int clas) throws SQLException {
        int count = st.executeUpdate("UPDATE STUDENTS SET CLASS =" + clas + " WHERE AGE =" + age);
        if (count == 0) {
            LOGGER.warn("В {} классе не было найдено учеников,которым {} лет",clas,age);
        }else {
            LOGGER.info("Операция прошла успешно.");
        }
        }
    }



