package net.fvogel.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.fvogel.model.typing.AtmosphereType;
import net.fvogel.model.typing.PlanetSurfaceType;
import net.fvogel.model.typing.PlanetType;

@Entity
@Data
public class Planet {

    @Id
    @GeneratedValue
    Long id;

    @JsonIgnore
    Timestamp lastUpdateAt = new Timestamp(System.currentTimeMillis());

    String name;

    PlanetType type;
    PlanetSurfaceType surface;
    AtmosphereType atmosphere;

    @ManyToOne
    @NotNull
    @JsonIgnore
    SolarSystem solarSystem;

    @ManyToOne
    Nation nation;

    short distance;

    short diameter;

    short tempLowerBound;
    short tempUpperBound;

    @OneToMany
    List<Resource> resources = new ArrayList<>();

}
