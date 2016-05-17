package models;

import javax.persistence.*;

@Entity
@Table(name = "datorii")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Long cif;
    public String denumire;

    @Column(name = "op_stat")
    public Integer opStat;

    @Column(name = "oa_stat")
    public Integer oaStat;

    @Column(name = "oc_stat")
    public Integer ocStat;

    @Column(name = "op_social")
    public Integer opSocial;

    @Column(name = "oa_social")
    public Integer oaSocial;

    @Column(name = "oc_social")
    public Integer ocSocial;

    @Column(name = "op_somaj")
    public Integer opSomaj;

    @Column(name = "oa_somaj")
    public Integer oaSomaj;

    @Column(name = "oc_somaj")
    public Integer ocSomaj;

    @Column(name = "op_sanatate")
    public Integer opSanatate;

    @Column(name = "oa_sanatate")
    public Integer oaSanatate;

    @Column(name = "oc_sanatate")
    public Integer ocSanatate;

    public Long total;


}
