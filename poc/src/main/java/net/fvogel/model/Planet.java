package net.fvogel.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.fvogel.model.typing.AtmosphereType;
import net.fvogel.model.typing.PlanetSurfaceType;
import net.fvogel.model.typing.PlanetType;
import org.hibernate.validator.constraints.NotEmpty;

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

    short hydrogenResources;
    short hydrogenMines;
    short hydrogenStock;

    short ironResources;
    short ironMines;
    short ironStock;

    short siliconResources;
    short siliconMines;
    short siliconStock;

}
