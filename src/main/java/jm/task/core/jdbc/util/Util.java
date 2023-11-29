package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String url = "jdbc:mysql://localhost/danilkhaertdinov";
    private static String username = "root";
    private static String password = "root";

    private Util() {

    }

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException exception) {
            System.out.println("SQL Exception, trying create new connection: " + exception);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            System.out.println("Connection to DB succesfull!");
            return connection;
        } catch (Exception e) {
            System.out.println("Connection failed...");
            System.out.println(e);
        }
        throw new RuntimeException();
    }

    public static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Connection close failed, but i think is mean nothing:)...");
                System.out.println(e);
            }
        }
    }


}
