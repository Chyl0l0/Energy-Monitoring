package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.entities.Device;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
public class AccountDetailsDTO {


    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @Pattern(regexp = "user|admin")
    private String role;
    @NotNull
    private String username;
    @NotNull
    private String password;

    private Set<Device> devices;
    public AccountDetailsDTO() {
    }

    public AccountDetailsDTO(UUID id, String name, String role, String username, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
    }
    public AccountDetailsDTO(UUID id, String name, String role, String username, String password, Set<Device> devices) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
        this.devices = devices;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDetailsDTO)) return false;
        AccountDetailsDTO that = (AccountDetailsDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getRole(), that.getRole()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRole(), getUsername(), getPassword());
    }
}
