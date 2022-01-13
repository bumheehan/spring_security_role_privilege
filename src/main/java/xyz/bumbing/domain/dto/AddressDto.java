package xyz.bumbing.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.bumbing.domain.entity.Address;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddressDto {
    private String zipCode;
    private String address1;
    private String address2;

    public static AddressDto of(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.zipCode = addressDto.getZipCode();
        addressDto.address1= addressDto.getAddress1();
        addressDto.address2= addressDto.getAddress2();
        return addressDto;
    }
}
