package net.fvogel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.fvogel.model.typing.AtmosphereType;
import net.fvogel.model.typing.PlanetSurfaceType;
import net.fvogel.model.typing.PlanetType;
import net.fvogel.model.typing.ResourceType;

@Entity
@Data
public class Planet {

    @Id
    @GeneratedValue
    Long id;

    String name;

    PlanetType type;
    PlanetSurfaceType surface;
    AtmosphereType atmosphere;

    @ManyToOne
    @NotNull
    @JsonIgnore
    SolarSystem solarSystem;

    @ManyToOne
    @JoinColumn(name = "nation_id")
    @JsonIgnore
    Nation nation;

    short distance;

    short diameter;

    short tempLowerBound;
    short tempUpperBound;

    @OneToMany(cascade = CascadeType.ALL)
    List<Resource> resources = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    List<Buildings> buildings = new ArrayList<>();

    public Resource getResource(ResourceType type) {
        return this.resources
                .stream()
                .filter((resource) -> {return type.equals(resource.getType());})
                .collect(Collectors.toList())
                .get(0);
    }

}
