package net.fvogel.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

    Integer mineCount;
    Integer mineCapacity;

    public Resource(ResourceType resourceType) {
        this.type = resourceType;
    }

}