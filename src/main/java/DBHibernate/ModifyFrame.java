package DBHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ModifyFrame implements ActionListener {

    private final JButton password;
    private final JButton fullName;
    private final JButton email;

    ModifyFrame(){

        password = new JButton();
        password.addActionListener(this);
        password.setBounds(85, 50, 100, 50);
        password.setText("Password");

        fullName = new JButton();
        fullName.addActionListener(this);
        fullName.setBounds(85, 100, 100, 50);
        fullName.setText("Full name");

        email = new JButton();
        email.addActionListener(this);
        email.setBounds(85, 150, 100, 50);
        email.setText("Email");

        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Database editor");
        frame.setLayout(null);
        frame.setSize(300, 300);
        frame.setVisible(true);

        frame.add(password);
        frame.add(fullName);
        frame.add(email);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        List<String> usernames = new ArrayList<>();
        for (int i = 0; i < DBActions.getData().size(); i++) {
            usernames.add(DBActions.getData().get(i).getUsername());
        }

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        try (Session session = sessionFactory.openSession()) {


            if (e.getSource() == password) {
                String username = null;


                while (username == null) {
                    username = JOptionPane.showInputDialog("Enter the username to modify password for");

                    if (usernames.contains(username)) {
                        Users user = DBActions.newUser(username);
                        DBActions.updatePassword(session, user);

                    } else {
                        JOptionPane.showMessageDialog(null, "User is not in database!");
                    }
                }
            }


            if (e.getSource() == fullName) {
                String username = null;


                while (username == null) {
                    username = JOptionPane.showInputDialog("Enter the username to modify full name for");

                    if (usernames.contains(username)) {
                        Users user = DBActions.newUser(username);
                        DBActions.updateFullName(session, user);

                    } else {
                        JOptionPane.showMessageDialog(null, "User is not in database!");
                    }
                }
            }
            if (e.getSource() == email) {
                String username = null;


                while (username == null) {
                    username = JOptionPane.showInputDialog("Enter the username to modify email for");

                    if (usernames.contains(username)) {
                        Users user = DBActions.newUser(username);
                        DBActions.updateEmail(session, user);

                    } else {
                        JOptionPane.showMessageDialog(null, "User is not in database!");
                    }
                }
            }
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, "Something went wrong!");
        }

    }
}
