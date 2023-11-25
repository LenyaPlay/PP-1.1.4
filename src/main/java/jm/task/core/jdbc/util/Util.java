package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private String url = "jdbc:mysql://localhost/danilkhaertdinov";
    private String username = "root";
    private String password = "root";


    Connection connection = null;

    public Connection getConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException exception) {
            System.out.println("SQL Exception, trying create new connection: " + exception);
        }

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to DB succesfull!");
            this.connection = connection;
            return connection;
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        return null;
    }



}
