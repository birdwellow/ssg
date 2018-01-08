package net.fvogel.model;

import lombok.Data;

@Data
public class AccountRegistration {

    String userName;
    String email;
    String password;
    String passwordConfirmation;

}
