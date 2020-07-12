package StudentsDataBase;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CreateDataBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateDataBase.class);
    public static void main(String[] args){
        try (Connection c = DriverManager.getConnection(Constants.URL,Constants.USERNAME,Constants.PASSWORD)) {
            try (Statement st = c.createStatement()) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS STUDENTS("
                        + "FIRST_NAME CHARACTER VARYING NOT NULL, "
                        + "LAST_NAME CHARACTER VARYING NOT NULL, "
                        + "AGE INTEGER NOT NULL, "
                        + "NUMBER CHARACTER VARYING NOT NULL, "
                        + "CLASS INTEGER NOT NULL, "
                        + "SUBCLASS CHARACTER VARYING NOT NULL, "
                        + "UNIQUE (NUMBER) "
                        + ")";
                st.execute(createTableSQL);
                List<String> names = new ArrayList(Files.readAllLines(Paths.get("name.txt")));
                List<String> surnames = new ArrayList(Files.readAllLines(Paths.get("surname.txt")));
                List<String> phones = new ArrayList(Files.readAllLines(Paths.get("phones.txt")));
                Collections.shuffle(names);
                Collections.shuffle(surnames);
                Collections.shuffle(phones);


                for (int i = 1; i <= 10; i++) {
                    String subclass = i <= 5 ? "A" : "B";
                    int clas = (int) (8 + Math.random() * 3);
                    st.executeUpdate(fillDataBase(names.get(i),surnames.get(i),15,phones.get(i),clas,subclass));
                }

            }


        }catch (SQLException e) {
            LOGGER.error(e.getMessage(),e);
        }catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }


    }


    private static String fillDataBase(String firstName, String lastName, int age, String phone, int clas,String subclass) {
        String insertTableSQL = "INSERT INTO STUDENTS"
                + "(FIRST_NAME, LAST_NAME, AGE, NUMBER, CLASS, SUBCLASS) " + "VALUES"
                + "('" + firstName + "','" + lastName + "'," + age + ",'" + phone + "'," + clas + ",'" + subclass + "')";
        return insertTableSQL;

    }
}
