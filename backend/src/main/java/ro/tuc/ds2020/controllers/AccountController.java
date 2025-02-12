package ro.tuc.ds2020.controllers;


import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.AccountDTO;
import ro.tuc.ds2020.dtos.AccountDetailsDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.services.AccountService;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        List<AccountDTO> dtos = accountService.findAccounts();
        for (AccountDTO dto : dtos) {
            Link accountLink = linkTo(methodOn(AccountController.class)
                    .getAccount(dto.getId())).withRel("accountDetails");
            dto.add(accountLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody AccountDetailsDTO accountDTO) {
        UUID accountID = accountService.insert(accountDTO);
        return new ResponseEntity<>(accountID, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UUID> updateAccount(@Valid @RequestBody AccountDetailsDTO accountDTO, @PathVariable("id") UUID accountId) {
        UUID accountID = accountService.update(accountDTO, accountId);
        return new ResponseEntity<>(accountID, HttpStatus.CREATED);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDetailsDTO> getAccount(@PathVariable("id") UUID accountId) {
        AccountDetailsDTO dto = accountService.findAccountById(accountId);
        System.out.println(accountId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") UUID accountId) {
        accountService.delete(accountId);
        return new ResponseEntity<>(accountId, HttpStatus.OK);
    }

    @PutMapping(value = "/{accountId}/{deviceId}")
    public ResponseEntity<?> addDevice(@PathVariable("accountId") UUID accountId,@PathVariable("deviceId") UUID deviceId ) {
        //UUID accountID = accountService.update(accountDTO, accountId);
        AccountDetailsDTO dto = accountService.addDevice(accountId,deviceId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{accountId}/devices")
    public ResponseEntity<?> getDevices(@PathVariable("accountId") UUID accountId){
        List<DeviceDTO> dtos = accountService.getDevicesById(accountId);
        for (DeviceDTO dto : dtos) {
            Link accountLink = linkTo(methodOn(AccountController.class)
                    .getAccount(dto.getId())).withRel("accountDetails");
            dto.add(accountLink);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @GetMapping(value = "/{username}/{password}")
    public ResponseEntity<?> addDevice(@PathVariable("username") String username,@PathVariable("password") String password ) {
        AccountDetailsDTO accountDetailsDTO = accountService.login(username, password);
        if (accountDetailsDTO == null){
            return new ResponseEntity<>(new AccountDetailsDTO(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(accountDetailsDTO, HttpStatus.CREATED);
    }






    }
