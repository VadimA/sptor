package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 31.01.2016.
 */
@Entity
@Table(name="toir.users")
@Proxy(lazy=false)
public class User {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int id;

    @NotEmpty(message = "Пожалуйтса введите ssoid")
    @Size(min=2, max=8, message = "ССОИД должен содержать не менее 2 символов")
    private String ssoid;


    @NotEmpty(message = "Пожалуйтса введите пароль")
    private String password;
    @NotEmpty(message = "Пожалуйтса введите имя")
    private String first_name;
    @NotEmpty(message = "Пожалуйтса введите фамилию")
    private String last_name;
    @NotEmpty(message = "Пожалуйтса введите email")
    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "toir.user_user_profile",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_profile_id") })
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getssoid() {
        return ssoid;
    }

    public void setssoid(String ssoid) {
        this.ssoid = ssoid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((ssoid == null) ? 0 : ssoid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        if (ssoid == null) {
            if (other.ssoid != null)
                return false;
        } else if (!ssoid.equals(other.ssoid))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", ssoId=" + ssoid + ", password=" + password
                + ", firstName=" + first_name + ", lastName=" + last_name
                + ", email=" + email + ", userProfiles=" + userProfiles +"]";
    }


}
