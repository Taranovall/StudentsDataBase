package StudentsDataBase;

import java.sql.SQLException;
import java.sql.Statement;

public interface ClassBook{
    void addStudent(Statement st,String firstName,String lastName,int age,String number,int clas,String subclass) throws SQLException;

    void removeStudent(Statement st,String lastName) throws SQLException;

    void getStudent(Statement st,String lastName) throws SQLException;

   void transferToAnotherClass(Statement st,int age,int clas) throws SQLException;
}
