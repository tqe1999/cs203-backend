package csd.cs203project.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private @Id @GeneratedValue Long id;

    private String name;
    private String email;
    private String userType;
    private String vaccinationStatus;
    private String swabTestResult;
    private String fetStatus;
    private String company;
    private String telegramHandle;
    private String telegramSignUpToken;
    private String telegramChatId;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;


    public User(String name, String userType, String company) {
        this.name = name;
        this.userType = userType;
        this.company = company;
    }

    public User(String email, String name, String userType, String company) {
        this.email = email;
        this.name = name;
        this.userType = userType;
        this.company = company;
    }

}
