package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.entities.Device;

public class DeviceBuilder {
    public DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMax_consumption(), device.getAccount());
    }
    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device){
        return new DeviceDetailsDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMax_consumption(), device.getAccount());
    }
    public static Device toEntity(DeviceDetailsDTO device){
        return new Device(device.getDescription(), device.getAddress(), device.getMax_consumption());

    }
}
