package net.fvogel.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.fvogel.model.typing.StarType;

@Entity
@Data
public class SolarSystem {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    String name;

    int coordX;
    int coordY;
    int coordZ;

    StarType type;

    @OneToMany
    List<Planet> planets = new ArrayList<>();

}
