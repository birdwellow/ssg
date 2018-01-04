package net.fvogel.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Data
public class Nation {

    @Id
    @GeneratedValue
    Long id;

    @NotNull
    @Size(min = 6, max = 31)
    @Column(unique = true)
    String name;

    @NotNull
    @Size(min = 6, max = 31)
    @Column(unique = true)
    String userName;

    @NotNull
    int credits;

    @NotNull
    @NotEmpty
    String uuid;

    @OneToMany
    List<Planet> planets = new ArrayList<>();

}
