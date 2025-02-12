package ro.tuc.ds2020.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Device  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "max_consumption", nullable = false)
    private float max_consumption;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    @OneToMany(mappedBy = "device", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<EnergyConsumption> energyConsumptions = new HashSet<>();

    public Device() {

    }

    public Device(String description, String address, float max_consumption) {
        this.description = description;
        this.address = address;
        this.max_consumption = max_consumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getMax_consumption() {
        return max_consumption;
    }

    public void setMax_consumption(Float max_consumption) {
        this.max_consumption = max_consumption;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setMax_consumption(float max_consumption) {
        this.max_consumption = max_consumption;
    }

    public Set<EnergyConsumption> getEnergyConsumptions() {
        return energyConsumptions;
    }

    public void setEnergyConsumptions(Set<EnergyConsumption> energyConsumptions) {
        this.energyConsumptions = energyConsumptions;
    }
}
