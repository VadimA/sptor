package com.diplom.sptor.model;

import com.diplom.sptor.domain.UserProfile;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by user on 12.04.2016.
 */
public class UserFormModel {

    @NotEmpty(message = "Пожалуйтса введите ssoid")
    @Size(min=2, max=8, message = "ССОИД должен содержать не менее 2 символов")
    private String ssoid;

    @NotEmpty(message = "Пожалуйтса введите пароль")
    @Size(min=6, max=15, message = "Пароль должен содержать не менее 2 символов")
    private String password;

    @NotEmpty(message = "Пожалуйтса введите имя")
    private String first_name;

    @NotEmpty(message = "Пожалуйтса введите фамилию")
    private String last_name;

    @NotEmpty(message = "Пожалуйтса введите e-mail")
    @Email
    private String email;


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

    @Override
    public String toString() {
        return "User [ssoId=" + ssoid + ", password=" + password
                + ", firstName=" + first_name + ", lastName=" + last_name
                + ", email=" + email + "]";
    }


}
