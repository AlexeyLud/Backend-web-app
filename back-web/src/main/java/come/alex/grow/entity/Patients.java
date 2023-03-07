package come.alex.grow.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id_pat;
    private String name;
    private String surname;
    private Integer age;
    @OneToMany(mappedBy = "patients") // child | | cascade = CascadeType.ALL
    private List<Recepts> recepts;

    public Patients(){}

    public Patients(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Integer getId_pat() {
        return id_pat;
    }
    public void setId_pat(Integer id_pat) {
        this.id_pat = id_pat;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getInfo(){
        return "|ID: " + id_pat + "\n|Name: " + name + "\n|Surname: " + surname +
                "\n|Age: " + age + "\n" + "-------------------";
    }
}