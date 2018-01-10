package net.fvogel.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Data
public class Nation {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @NotNull
    @Size(min = 6, max = 31)
    @Column(unique = true)
    String name;

    @NotNull
    int credits;

    @OneToMany
    List<Planet> planets = new ArrayList<>();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "account_id")
    Account account;

}
