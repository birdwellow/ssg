package net.fvogel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @NotNull
    @Size(min = 6, max = 31)
    @Column(unique = true)
    String userName;

    @Email
    @NotNull
    String email;

    String password;

    @Transient
    String passwordConfirm;


}
