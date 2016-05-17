package models;



import javax.persistence.*;

@Entity
@Table(name = "firme")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Long cif;
    public String denumire;
    public String cod;
    public String stari;
    public String judet;
    public String localitate;

}