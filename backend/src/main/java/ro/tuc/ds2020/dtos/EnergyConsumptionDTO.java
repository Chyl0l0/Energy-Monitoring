package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Account;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
public class EnergyConsumptionDTO extends RepresentationModel<DeviceDTO> {

    private UUID id;

    private Timestamp timestamp;

    private float energy_consumption;

    private Device device;
    public EnergyConsumptionDTO() {
    }
    public EnergyConsumptionDTO(UUID id,Timestamp timestamp, float energy_consumption, Device device) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnergyConsumptionDTO)) return false;
        if (!super.equals(o)) return false;
        EnergyConsumptionDTO that = (EnergyConsumptionDTO) o;
        return Float.compare(that.getEnergy_consumption(), getEnergy_consumption()) == 0 && Objects.equals(getId(), that.getId()) && Objects.equals(getTimestamp(), that.getTimestamp()) && Objects.equals(getDevice(), that.getDevice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getTimestamp(), getEnergy_consumption(), getDevice());
    }
}
