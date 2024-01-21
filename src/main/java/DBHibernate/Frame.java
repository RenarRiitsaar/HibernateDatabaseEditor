package DBHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {

    private final JButton modify;

    JButton input;
    JButton view;


    JButton delete;
    JLabel label = new JLabel();

    Frame() {

        label.setText("Available actions:");
        label.setBounds(85, 10, 100, 50);
        label.setVisible(true);

        input = new JButton();
        input.addActionListener(this);
        input.setBounds(85, 50, 100, 50);
        input.setText("Input");

        view = new JButton();
        view.addActionListener(this);
        view.setBounds(85, 100, 100, 50);
        view.setText("View");

        modify = new JButton();
        modify.addActionListener(this);
        modify.setBounds(85, 150, 100, 50);
        modify.setText("Modify");

        delete = new JButton();
        delete.addActionListener(this);
        delete.setBounds(85, 200, 100, 50);
        delete.setText("Delete");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Database editor");
        this.setLayout(null);
        this.setSize(300, 300);
        this.setVisible(true);

        this.add(label);
        this.add(input);
        this.add(view);
        this.add(modify);
        this.add(delete);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            if (e.getSource() == view) {
                DBActions.displayUsers(session);
            }
            if (e.getSource() == delete) {
                String username = JOptionPane.showInputDialog("Enter the username to delete:");
                Users user = DBActions.newUser(username);
                DBActions.deleteUser(session, user);

            }
            if (e.getSource() == input) {
                DBActions.insertUser(session);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (e.getSource() == modify) {
            new ModifyFrame();

        }
    }
}
