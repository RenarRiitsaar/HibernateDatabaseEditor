package DBHibernate;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DBActions {

    @Getter
    private static List<Users> data;

    public static void initializeData(){

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Users.class).buildSessionFactory();

        try(Session session = sessionFactory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
            criteria.from(Users.class);
            data = session.createQuery(criteria).getResultList();

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Something went wrong!");
        }

    }

    public static void insertUser(Session session){
            Transaction transaction = session.beginTransaction();
            String username = null;
            String password = null;
            String fullName = null;
            String email = null;

            while(username == null) {
                username = JOptionPane.showInputDialog("Enter a new username");

                if(username.isEmpty()){
                    username = null;
                    JOptionPane.showMessageDialog(null,"Username field cannot be empty!");
                }
                for(int i = 0; i<data.size(); i++){

                    if(Objects.equals(data.get(i).getUsername(), username)){
                        username = null;
                        JOptionPane.showMessageDialog(null,"Username is already in database");
                    }
                }
            }

            while(password == null) {
                password = JOptionPane.showInputDialog("Enter a password");
                if (password.isEmpty()) {
                    password = null;
                    JOptionPane.showMessageDialog(null, "Password field cannot be empty");
                }
            }


            while(fullName == null){
                 fullName = JOptionPane.showInputDialog("Enter a full name");

                 if(fullName.isEmpty()){
                     fullName = null;
                     JOptionPane.showMessageDialog(null, "Full name field cannot be empty");
                 }
            }

            while(email == null) {
                 email = JOptionPane.showInputDialog("Enter an email");

                 if(email.isEmpty()){
                     email = null;
                     JOptionPane.showMessageDialog(null, "Email field cannot be empty");
                 }
            }
            Users user = new Users(username,password,fullName,email);

                session.persist(user);
            transaction.commit();

            if(user !=null){
                JOptionPane.showMessageDialog(null, "User successfully inserted to database!");
            }else{
                JOptionPane.showMessageDialog(null, "Something went wrong!");
            }
    }

    public static void deleteUser(Session session, Users user){


            Transaction transaction = session.beginTransaction();


            Users retrievedUser = session.get(Users.class,user.getUsername());
                       if(retrievedUser == null){
                JOptionPane.showMessageDialog(null, "Couldn't find user from database!");

            }else{
                JOptionPane.showMessageDialog(null, "User deleted from database!");
            }

            session.remove(retrievedUser);
            transaction.commit();
    }

    public static void updateEmail(Session session, Users user){

            Users retrieve = session.get(Users.class, user.getUsername());
            Transaction transaction = session.beginTransaction();
            String email = JOptionPane.showInputDialog("Enter a new email:");
            retrieve.setEmail(email);
            session.merge(retrieve);
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Email successfully changed!");

    }
    public static void updateFullName(Session session, Users user){


            Users retrieve = session.get(Users.class, user.getUsername());
            Transaction transaction = session.beginTransaction();
            String fullName = JOptionPane.showInputDialog("Enter a new full name:");
            retrieve.setFullName(fullName);
            session.merge(retrieve);
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Full name successfully changed!");
    }
    public static void updatePassword(Session session, Users user) {

        String password = null;

            Users usr = session.get(Users.class, user.getUsername());
            Transaction transaction = session.beginTransaction();

            while(password == null) {
                password = JOptionPane.showInputDialog("Enter a new password: ");

                if(password.isEmpty()){
                    password = null;
                    JOptionPane.showMessageDialog(null, "Password field cannot be empty");
                }
            }
            usr.setPassword(password);
            session.merge(usr);
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Password successfully changed!");

    }

    public static Users newUser(String username) {

        return new Users(username);

    }

    public static void displayUsers(Session session) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
            criteria.from(Users.class);
        data = session.createQuery(criteria).getResultList();
            WriteToTxt.Write(data.toString());

            File file = null;

            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file = new File("src/main/resources/userData.txt"));
                Thread.sleep(2000);

            } catch (IOException | InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Something went wrong!");
            } finally {
                assert file != null;
                file.delete();
            }
    }
}