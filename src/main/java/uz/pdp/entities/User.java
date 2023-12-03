package uz.pdp.entities;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    private  String userName;
    private  String email;
    private  String password;
    private LocalDateTime registrationDate;

    {
        System.out.println(registrationDate = LocalDateTime.now());
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

}
