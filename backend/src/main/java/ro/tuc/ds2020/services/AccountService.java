package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.AccountDTO;
import ro.tuc.ds2020.dtos.AccountDetailsDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.builders.AccountBuilder;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Account;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.AccountRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, DeviceRepository deviceRepository) {
        this.accountRepository = accountRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<AccountDTO> findAccounts() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream()
                .map(AccountBuilder::toAccountDTO)
                .collect(Collectors.toList());
    }

    public AccountDetailsDTO findAccountById(UUID id) {
        Optional<Account> prosumerOptional = accountRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Account with id {} was not found in db", id);
            throw new ResourceNotFoundException(Account.class.getSimpleName() + " with id: " + id);
        }
        return AccountBuilder.toAccountDetailsDTO(prosumerOptional.get());
    }

    public UUID insert(AccountDetailsDTO accountDTO) {
        Account account = AccountBuilder.toEntity(accountDTO);
        account = accountRepository.save(account);
        LOGGER.debug("Account with id {} was inserted in db", account.getId());
        return account.getId();
    }
    public void delete(UUID id) {
        Optional<Account> prosumerOptional = accountRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Account with id {} was not found in db", id);
            throw new ResourceNotFoundException(Account.class.getSimpleName() + " with id: " + id);
        }
        accountRepository.deleteById(id);
        LOGGER.debug("Account with id {} was deleted from db", id);
    }

    public UUID update(AccountDetailsDTO accountDTO, UUID id) {
        Account account = AccountBuilder.toEntity(accountDTO);
        Optional<Account> prosumerOptional = accountRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Account with id {} was not found in db", id);
            throw new ResourceNotFoundException(Account.class.getSimpleName() + " with id: " + id);
        }
        account.setId(id);
        account = accountRepository.save(account);
        LOGGER.debug("Account with id {} was updated in db", account.getId());
        return account.getId();
    }
    //@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public AccountDetailsDTO addDevice(UUID accountId, UUID deviceId){
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (!accountOptional.isPresent()) {
            LOGGER.error("Account with id {} was not found in db", accountId);
            throw new ResourceNotFoundException(Account.class.getSimpleName() + " with id: " + accountId);
        }
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", deviceId);
            throw new ResourceNotFoundException(Account.class.getSimpleName() + " with id: " + deviceId);
        }
        Account account = accountOptional.get();

        Set<Device>devices = account.getDevices();
        devices.add(deviceOptional.get());
        account.setDevices(devices);
        account = accountRepository.save(account);

        Device device = deviceOptional.get();
        device.setAccount(account);
        deviceRepository.save(device);

        System.out.println(account.getDevices());
        return AccountBuilder.toAccountDetailsDTO(account);
    }

    public List<DeviceDTO> getDevicesById(UUID accountId){
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (!accountOptional.isPresent()) {
            LOGGER.error("Account with id {} was not found in db", accountId);
            throw new ResourceNotFoundException(Account.class.getSimpleName() + " with id: " + accountId);
        }

        return accountOptional.get().getDevices().stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());

    }

    public AccountDetailsDTO login(String username, String password){
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (!accountOptional.isPresent()) {
            LOGGER.error("Account with username {} was not found in db", username);
            throw new ResourceNotFoundException(Account.class.getSimpleName() + " with id: " + username);
        }
        if(!accountOptional.get().getPassword().equals(password))
            return null;
        return AccountBuilder.toAccountDetailsDTO(accountOptional.get());

    }









}
