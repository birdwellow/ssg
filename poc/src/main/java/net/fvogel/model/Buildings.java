package net.fvogel.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.fvogel.model.typing.BuildingType;

@Entity
@Data
@NoArgsConstructor
public class Buildings {

    @Id
    @GeneratedValue
    Long id;

    @JsonIgnore
    Long lastUpdatedAt = System.currentTimeMillis();

    BuildingType type;

    Integer count;
    Integer buildingCount;

    Long nextBuildingFinished;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Planet planet;

    @JsonIgnore
    @OneToOne(mappedBy = "mines", cascade = CascadeType.ALL)
    Resource resource;
}
