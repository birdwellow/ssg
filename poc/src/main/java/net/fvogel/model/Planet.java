package net.fvogel.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import net.fvogel.model.typing.PlanetType;

@Entity
@Data
public class Planet {

    @Id
    @GeneratedValue
    Long id;

    Timestamp lastUpdateAt = new Timestamp(System.currentTimeMillis());

    String name;

    PlanetType type;

    @ManyToOne
    @NotNull
    SolarSystem solarSystem;

    @ManyToOne
    Nation nation;

    short distance;

    short diameter;

    short hydrogenCapacity;
    short ironCapacity;
    short siliconCapacity;

    short hydrogenMines;
    short ironMines;
    short siliconMines;

}
