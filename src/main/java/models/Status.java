package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stari")
public class Status {

    @Id
    public Integer cod;
    public String denumire;
}
