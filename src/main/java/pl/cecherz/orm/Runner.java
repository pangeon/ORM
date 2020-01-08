package pl.cecherz.orm;

import org.hibernate.Session;
import pl.cecherz.orm.model.User;
import pl.cecherz.orm.utils.SessionController;

import org.hibernate.Transaction;;import java.util.List;

public class Runner {
    public static void main(String[] args) {
        User user_kamil = new User("Kamil", "Cecherz", "kamil@cecherz.pl");
        User user_lukas = new User("Lukasz", "Bednarski", "bed46@wp.pl");
        User user_agnieszka = new User("Agnieszka", "Lasota", "agnieszka.lasota1@vp.pl");
        Transaction transaction = null;

        try(Session session = SessionController.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user_kamil);
            session.save(user_lukas);
            session.save(user_agnieszka);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        try(Session session = SessionController.getSessionFactory().openSession()) {
            List<User> userList = session.createQuery("FROM User", User.class).list();
            userList.forEach(user -> System.out.println(user.getName() + " " + user.getSurname() + " " + user.getEmail()));
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

