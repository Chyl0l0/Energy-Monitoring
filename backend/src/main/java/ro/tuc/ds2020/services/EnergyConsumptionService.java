package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.EnergyConsumptionDTO;
import ro.tuc.ds2020.dtos.EnergyConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.builders.EnergyConsumptionBuilder;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.repositories.EnergyConsumptionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnergyConsumptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyConsumptionService.class);
    private final EnergyConsumptionRepository energyConsumptionRepository;

    @Autowired
    public EnergyConsumptionService(EnergyConsumptionRepository energyConsumptionRepository) {
        this.energyConsumptionRepository = energyConsumptionRepository;
    }

    public List<EnergyConsumptionDTO> findEnergyConsumptions() {
        List<EnergyConsumption> energyConsumptionList = energyConsumptionRepository.findAll();
        return energyConsumptionList.stream()
                .map(EnergyConsumptionBuilder::toEnergyConsumptionDTO)
                .collect(Collectors.toList());
    }

    public EnergyConsumptionDetailsDTO findEnergyConsumptionById(UUID id) {
        Optional<EnergyConsumption> prosumerOptional = energyConsumptionRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("EnergyConsumption with id {} was not found in db", id);
            throw new ResourceNotFoundException(EnergyConsumption.class.getSimpleName() + " with id: " + id);
        }
        return EnergyConsumptionBuilder.toEnergyConsumptionDetailsDTO(prosumerOptional.get());
    }

    public UUID insert(EnergyConsumptionDetailsDTO energyConsumptionDTO) {
        EnergyConsumption energyConsumption = EnergyConsumptionBuilder.toEntity(energyConsumptionDTO);
        energyConsumption = energyConsumptionRepository.save(energyConsumption);
        LOGGER.debug("EnergyConsumption with id {} was inserted in db", energyConsumption.getId());
        return energyConsumption.getId();
    }
    public void delete(UUID id) {
        Optional<EnergyConsumption> prosumerOptional = energyConsumptionRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("EnergyConsumption with id {} was not found in db", id);
            throw new ResourceNotFoundException(EnergyConsumption.class.getSimpleName() + " with id: " + id);
        }
        energyConsumptionRepository.deleteById(id);
        LOGGER.debug("EnergyConsumption with id {} was deleted from db", id);
    }

    public UUID update(EnergyConsumptionDetailsDTO energyConsumptionDTO, UUID id) {
        EnergyConsumption energyConsumption = EnergyConsumptionBuilder.toEntity(energyConsumptionDTO);
        Optional<EnergyConsumption> prosumerOptional = energyConsumptionRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("EnergyConsumption with id {} was not found in db", id);
            throw new ResourceNotFoundException(EnergyConsumption.class.getSimpleName() + " with id: " + id);
        }
        energyConsumption.setId(id);
        energyConsumption = energyConsumptionRepository.save(energyConsumption);
        LOGGER.debug("EnergyConsumption with id {} was updated in db", energyConsumption.getId());
        return energyConsumption.getId();
    }





}
