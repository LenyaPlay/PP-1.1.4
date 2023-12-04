package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {

    public static void main(String[] args) {
        UserService database = new UserServiceImpl();
        database.createUsersTable();
        addUserToDatabase(database, "name1", "lastname1", (byte) 12);
        addUserToDatabase(database, "name2", "lastname2", (byte) 15);
        addUserToDatabase(database, "name3", "lastname3", (byte) 20);
        addUserToDatabase(database, "name4", "lastname4", (byte) 25);
        var users = database.getAllUsers();
        for (var user : users) {
            System.out.println(user.toString());
        }
        database.cleanUsersTable();
        database.dropUsersTable();
        Util.closeAll();
    }

    private static void addUserToDatabase(UserService database, String name, String lastName, byte age) {
        database.saveUser(name, lastName, age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }
}
