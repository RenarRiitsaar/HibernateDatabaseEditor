package DBHibernate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class Users {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;
    @Id
  @Column(name = "username")
    private String username;
  @Column(name = "password")
    private String password;
  @Column(name = "fullName")
    private String fullName;
    @Column(name = "email")
    private String email;

    Users(String username, String password, String fullName, String email) {
    this.username = username;
    this.password = password;
    this.fullName = fullName;
    this.email = email;

    }
    Users(){

    }

    public Users(String username) {
        this.username =username;
    }

    @Override
    public String toString() {
        return  "userID= " + userID +
                "\nusername= " + username +
                "\npassword= " + password  +
                "\nfullName= " + fullName +
                "\nemail= " + email + "\n" + "\n";
    }
}
