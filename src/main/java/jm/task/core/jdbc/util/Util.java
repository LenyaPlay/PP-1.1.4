package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static String url = "jdbc:mysql://localhost/danilkhaertdinov";
    private static String username = "root";
    private static String password = "root";
    private static SessionFactory sessionFactory = null;
    private static Connection connection = null;

    private Util() {

    }

    public static Session getSession() {
        if (sessionFactory != null) {
            return sessionFactory.getCurrentSession();
        }
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(getHibernateSettings()).build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(User.class);
        Metadata metadata = metadataSources.buildMetadata();
        sessionFactory = metadata.buildSessionFactory();
        return sessionFactory.getCurrentSession();
    }

    private static Map<String, String> getHibernateSettings() {
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
        settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        settings.put("hibernate.connection.url", url);
        settings.put("hibernate.connection.username", username);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");
        return settings;
    }

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
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Connection close failed, but i think is mean nothing:)...");
            e.printStackTrace();
        }

    }

    public static void closeSession() {
        try {
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        } catch (HibernateException e) {
            System.out.println("Session close failed, but i think is mean nothing:)...");
            e.printStackTrace();
        }
    }

    /**
     * Closes all open connections and open sessions
     */
    public static void closeAll() {
        closeConnection();
        closeSession();
    }

}
