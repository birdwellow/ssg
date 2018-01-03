package net.fvogel.model.meta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class MetaInitStatus {

    @Id
    @GeneratedValue
    Long id;

}
