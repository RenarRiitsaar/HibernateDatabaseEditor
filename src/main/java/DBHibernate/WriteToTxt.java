package DBHibernate;


import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToTxt {

        public static void Write(String data){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/userData.txt", true))){
                writer.write(data);
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, "Something went wrong!");
            }
        }
    }

