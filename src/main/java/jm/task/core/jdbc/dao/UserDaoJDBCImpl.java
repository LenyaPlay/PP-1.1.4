package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String createTableIfNotExist = "CREATE TABLE IF NOT EXISTS user (" + "id BIGINT PRIMARY KEY AUTO_INCREMENT," + "name VARCHAR(255) NOT NULL," + "lastName VARCHAR(255) NOT NULL," + "age TINYINT)";
            statement.execute(createTableIfNotExist);
            Util.getConnection().commit();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS user";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(dropTableSQL)) {
            preparedStatement.executeUpdate();
            Util.getConnection().commit();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            String insertUserSQL = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(insertUserSQL)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);

                preparedStatement.executeUpdate();
                Util.getConnection().commit();
            } catch (SQLException e) {
                Util.getConnection().rollback();
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void removeUserById(long id) {

        String deleteUserSQL = "DELETE FROM user WHERE id = ?";

        try {
            try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(deleteUserSQL)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                Util.getConnection().commit();
            } catch (SQLException e) {
                Util.getConnection().rollback();
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAllUsersSQL = "SELECT * FROM user";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(getAllUsersSQL); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String cleanTableSQL = "DELETE FROM user";
        try {
            try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(cleanTableSQL)) {
                preparedStatement.executeUpdate();
                Util.getConnection().commit();
            } catch (SQLException e) {
                Util.getConnection().rollback();
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
