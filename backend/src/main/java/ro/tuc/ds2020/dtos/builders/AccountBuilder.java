package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.AccountDTO;
import ro.tuc.ds2020.dtos.AccountDetailsDTO;
import ro.tuc.ds2020.entities.Account;

public class AccountBuilder {

    private AccountBuilder() {
    }

    public static AccountDTO toAccountDTO(Account account) {
        return new AccountDTO(account.getId(), account.getName(), account.getRole(), account.getUsername(), account.getPassword());
    }
    public static AccountDetailsDTO toAccountDetailsDTO(Account account) {
        return new AccountDetailsDTO(account.getId(), account.getName(), account.getRole(), account.getUsername(), account.getPassword(), account.getDevices());
    }

    public static Account toEntity(AccountDetailsDTO account) {
        return new Account(account.getName(), account.getRole(), account.getUsername(), account.getPassword(), account.getDevices());
    }

}
