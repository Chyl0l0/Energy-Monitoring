package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Account;
import ro.tuc.ds2020.entities.EnergyConsumption;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private UUID id;

    private String description;

    private String address;

    private float max_consumption;

    private Account account;

    private Set<EnergyConsumption> energyConsumptions;

    public DeviceDTO() {

    }

    public DeviceDTO(UUID id,String description, String address, float max_consumption, Account account) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.max_consumption = max_consumption;
        this.account = account;
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

    public void setMax_consumption(float max_consumption) {
        this.max_consumption = max_consumption;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<EnergyConsumption> getEnergyConsumptions() {
        return energyConsumptions;
    }

    public void setEnergyConsumptions(Set<EnergyConsumption> energyConsumptions) {
        this.energyConsumptions = energyConsumptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceDTO)) return false;
        if (!super.equals(o)) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return Float.compare(deviceDTO.getMax_consumption(), getMax_consumption()) == 0 && Objects.equals(getId(), deviceDTO.getId()) && Objects.equals(getDescription(), deviceDTO.getDescription()) && Objects.equals(getAddress(), deviceDTO.getAddress()) && Objects.equals(getAccount(), deviceDTO.getAccount()) && Objects.equals(getEnergyConsumptions(), deviceDTO.getEnergyConsumptions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getDescription(), getAddress(), getMax_consumption(), getAccount(), getEnergyConsumptions());
    }
}
