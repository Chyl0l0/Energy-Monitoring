package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.EnergyConsumption;

import java.util.UUID;

public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, UUID> {

    /**
     * Example: JPA generate Query by Field
     */
}
