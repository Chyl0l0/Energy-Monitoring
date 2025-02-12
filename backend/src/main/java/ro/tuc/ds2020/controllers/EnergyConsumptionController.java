package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.EnergyConsumptionDTO;
import ro.tuc.ds2020.dtos.EnergyConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.EnergyConsumptionDTO;
import ro.tuc.ds2020.services.EnergyConsumptionService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/energy")
public class EnergyConsumptionController {

    private final EnergyConsumptionService energyConsumptionService;

    @Autowired
    public EnergyConsumptionController(EnergyConsumptionService energyConsumptionService) {
        this.energyConsumptionService = energyConsumptionService;
    }

    @GetMapping()
    public ResponseEntity<List<EnergyConsumptionDTO>> getEnergyConsumptions() {
        List<EnergyConsumptionDTO> dtos = energyConsumptionService.findEnergyConsumptions();
        for (EnergyConsumptionDTO dto : dtos) {
            Link energyConsumptionLink = linkTo(methodOn(EnergyConsumptionController.class)
                    .getEnergyConsumption(dto.getId())).withRel("energyConsumptionDetails");
            dto.add(energyConsumptionLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody EnergyConsumptionDetailsDTO energyConsumptionDTO) {
        UUID energyConsumptionID = energyConsumptionService.insert(energyConsumptionDTO);
        return new ResponseEntity<>(energyConsumptionID, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UUID> updateEnergyConsumption(@Valid @RequestBody EnergyConsumptionDetailsDTO energyConsumptionDTO, @PathVariable("id") UUID energyConsumptionId) {
        UUID energyConsumptionID = energyConsumptionService.update(energyConsumptionDTO, energyConsumptionId);
        return new ResponseEntity<>(energyConsumptionID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EnergyConsumptionDetailsDTO> getEnergyConsumption(@PathVariable("id") UUID energyConsumptionId) {
        EnergyConsumptionDetailsDTO dto = energyConsumptionService.findEnergyConsumptionById(energyConsumptionId);
        System.out.println(energyConsumptionId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteEnergyConsumption(@PathVariable("id") UUID energyConsumptionId) {
        energyConsumptionService.delete(energyConsumptionId);
        return new ResponseEntity<>(energyConsumptionId, HttpStatus.OK);
    }

//    @GetMapping(value = "/{energyConsumptionId}/energy")
//    public ResponseEntity<?> getEnergy(@PathVariable("energyConsumptionId") UUID energyConsumptionId){
//        List<EnergyConsumptionDTO> dtos = energyConsumptionService.getEnergyConsumptionById(energyConsumptionId);
//        for (EnergyConsumptionDTO dto : dtos) {
//            Link energyLink = linkTo(methodOn(EnergyConsumptionController.class)
//                    .getEnergyConsumption(dto.getId())).withRel("energyConsumptionDetails");
//            dto.add(energyLink);
//        }
//        return new ResponseEntity<>(dtos, HttpStatus.OK);
//    }

}
