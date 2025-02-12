package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class EnergyConsumption  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "energy_consumption", nullable = false)
    private float energy_consumption;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="device_id", nullable=false)
    private Device device;

    public EnergyConsumption() {
    }

    public EnergyConsumption(Timestamp timestamp, float energy_consumption, Device device) {
        this.timestamp = timestamp;
        this.energy_consumption = energy_consumption;
        this.device = device;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public float getEnergy_consumption() {
        return energy_consumption;
    }

    public void setEnergy_consumption(float energy_consumption) {
        this.energy_consumption = energy_consumption;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
