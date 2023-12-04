package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            var transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS user (" +
                            "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR(255) NOT NULL," +
                            "lastName VARCHAR(255) NOT NULL," +
                            "age TINYINT)")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            var transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User where id=" + id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
