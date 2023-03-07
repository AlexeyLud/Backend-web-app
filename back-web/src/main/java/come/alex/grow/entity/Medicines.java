package come.alex.grow.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "medicines")
public class Medicines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id_med;
    private String name;
    private Date date;
    private Integer doza;
    @OneToMany(mappedBy = "medicines") // child | cascade = CascadeType.ALL
    private List<Recepts> recepts;

    public Medicines(){}

    public Medicines(String name, Date date, Integer doza){
        this.name = name;
        this.date = date;
        this.doza = doza;
    }

    public Integer getId_med() {
        return id_med;
    }
    public void setId_med(Integer id_med) {
        this.id_med = id_med;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Integer getDoza() {
        return doza;
    }
    public void setDoza(Integer doza) {
        this.doza = doza;
    }

    public String getInfo(){
        return "|ID: " + id_med + "\n|Name: " + name + "\n|Date: " + date +
                "\n|Doza: " + doza + "мг\n" + "-------------------";
    }

}
