package net.fvogel.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.fvogel.model.typing.ResourceType;

@Entity
@Data
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @JsonIgnore
    Long lastUpdatedAt = System.currentTimeMillis();

    ResourceType type;

    @ManyToOne
    @JsonIgnore
    Planet planet;

    Double stock;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mine_buildings_id")
    Buildings mines;

    Integer mineCapacity;

    public Resource(ResourceType resourceType) {
        this.type = resourceType;
    }

}
