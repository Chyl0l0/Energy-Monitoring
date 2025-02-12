package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.EnergyConsumptionDTO;
import ro.tuc.ds2020.dtos.EnergyConsumptionDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;

public class EnergyConsumptionBuilder {
    public EnergyConsumptionBuilder() {
    }
    public static EnergyConsumptionDTO toEnergyConsumptionDTO(EnergyConsumption energyConsumption) {
        return new EnergyConsumptionDTO(energyConsumption.getId(), energyConsumption.getTimestamp(), energyConsumption.getEnergy_consumption(), energyConsumption.getDevice());
    }
    public static EnergyConsumptionDetailsDTO toEnergyConsumptionDetailsDTO(EnergyConsumption energyConsumption){
        return new EnergyConsumptionDetailsDTO(energyConsumption.getId(), energyConsumption.getTimestamp(), energyConsumption.getEnergy_consumption(), energyConsumption.getDevice());
    }
    public static EnergyConsumption toEntity(EnergyConsumptionDetailsDTO energyConsumption){
        return new EnergyConsumption(energyConsumption.getTimestamp(), energyConsumption.getEnergy_consumption(), energyConsumption.getDevice());

    }
}
