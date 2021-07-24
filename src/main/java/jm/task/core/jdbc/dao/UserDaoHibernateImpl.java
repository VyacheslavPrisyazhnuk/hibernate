package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), lastName VARCHAR(20), age INT)")
                .addEntity(User.class)
                .executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users")
                .executeUpdate();
        tx.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            SessionFactory fg = Util.getSessionFactory();
            Session session = fg.openSession();
            System.out.println(fg.isOpen());
            System.out.println("Добавление записи в таблицу БД");
            Transaction tx = session.beginTransaction();
                User person = new User(name, lastName, age);
                session.save(person);
            tx.commit();
            System.out.println("\tЗаписи добавлены");
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        tx.commit();
        session.close();
    }
    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List <User> list;
        list = session.createCriteria(User.class).list();
        for (User item: list) {
            System.out.println(item);
        }
        tx.commit();
        session.close();
            return list;
        }
    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        final List<User> list = session.createCriteria(User.class).list();
        if(list.size() > 0) {
            for (User obj : list) {
                session.delete(obj);
            }
        }
            tx.commit();
            session.close();
        }
    }

