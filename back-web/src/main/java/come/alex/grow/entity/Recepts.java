package come.alex.grow.entity;

import javax.persistence.*;
@Entity
@Table(name = "recepts")
public class Recepts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicines_id")
    private Medicines medicines;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patients_id")
    private Patients patients;
    private String signature;

    public Recepts() {
    }
    public Recepts(Medicines med_id, Patients pat_id, String sign){
        this.medicines = med_id;
        this.patients = pat_id;
        this.signature = sign;
    }

    public Patients getPatients() {
        return patients;
    }
    public void setPatients(Patients patients) {
        this.patients = patients;
    }
    public Medicines getMedicines() {
        return medicines;
    }
    public void setMedicines(Medicines medicines) {
        this.medicines = medicines;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getInfo(){
        return "|ID: " + id + "\n|Medicine_ID: " + medicines.getId_med() + "\n|Patient_ID: " +
                patients.getId_pat() + "\n|Signature: " + signature + "\n" + "------------------";
    }

}

