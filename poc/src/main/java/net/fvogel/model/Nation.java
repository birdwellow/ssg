package net.fvogel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Nation {

    @Id
    @GeneratedValue
    Long id;

    @NotNull
    String name;

    @NotNull
    String userName;

    int credits;

    @NotNull
    UUID uuid;

    @OneToMany
    List<Planet> planets = new ArrayList<>();

}
