package com.adminplatform.los_auth.party.service;

import com.adminplatform.los_auth.party.dto.PartyAddressDto;
import com.adminplatform.los_auth.party.entity.Address;
import com.adminplatform.los_auth.party.entity.PartyAddress;
import com.adminplatform.los_auth.party.entity.PartyAddressId;
import com.adminplatform.los_auth.party.repository.AddressRepository;
import com.adminplatform.los_auth.party.repository.PartyAddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PartyAddressService {

    private final PartyAddressRepository partyAddressRepo;
    private final AddressRepository addressRepo;

    public PartyAddressService(PartyAddressRepository partyAddressRepo, AddressRepository addressRepo) {
        this.partyAddressRepo = partyAddressRepo;
        this.addressRepo = addressRepo;
    }

    public List<PartyAddressDto> getAddresses(UUID tenantId, UUID partyId) {
        return partyAddressRepo.findByIdTenantIdAndIdPartyId(tenantId, partyId)
                .stream()
                .map(pa -> {
                    Address a = pa.getAddress();
                    return new PartyAddressDto(
                            pa.getId().getPartyId(),
                            pa.getId().getAddressId(),
                            pa.getKind(),
                            pa.getId().getEffectiveFrom(),
                            pa.getEffectiveTo(),
                            a.getLine1(),
                            a.getLine2(),
                            a.getCity(),
                            a.getProvinceCode(),
                            a.getPostalCode(),
                            a.getCountryCode(),
                            a.getGeocode()
                    );
                })
                .collect(Collectors.toList());
    }
    @Transactional
    public PartyAddressDto addAddress(UUID tenantId, UUID partyId, PartyAddressDto dto) {
        System.out.println("Inside createAddress");
        Address address = new Address();
        address.setAddressId(dto.addressId());
        address.setTenantId(tenantId);
        address.setLine1(dto.line1());
        address.setLine2(dto.line2());
        address.setCity(dto.city());
        address.setProvinceCode(dto.provinceCode());
        address.setPostalCode(dto.postalCode());
        address.setCountryCode(dto.countryCode());
        address.setGeocode(dto.geocode());

        addressRepo.save(address);


        PartyAddressId id = new PartyAddressId(tenantId, partyId, dto.addressId(), dto.effectiveFrom());
        PartyAddress pa = new PartyAddress();
        pa.setId(id);
        pa.setKind(dto.kind());
        pa.setEffectiveTo(dto.effectiveTo());
        pa.setAddress(address);

        partyAddressRepo.save(pa);

        System.out.println("DTO received: " + dto);
        System.out.println("Saving Address: " + address);
        System.out.println("Saving PartyAddress: " + pa);


        return dto;
    }

    @Transactional
    public PartyAddressDto updateAddress(UUID tenantId, UUID partyId, PartyAddressDto dto) {
        PartyAddressId id = new PartyAddressId(tenantId, partyId, dto.addressId(), dto.effectiveFrom());

        PartyAddress pa = partyAddressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PartyAddress not found"));

        // ✅ Update PartyAddress fields
        pa.setKind(dto.kind());
        pa.setEffectiveTo(dto.effectiveTo());

        // ✅ Update linked Address entity
        Address address = pa.getAddress();
        address.setLine1(dto.line1());
        address.setLine2(dto.line2());
        address.setCity(dto.city());
        address.setProvinceCode(dto.provinceCode());
        address.setPostalCode(dto.postalCode());
        address.setCountryCode(dto.countryCode());
        address.setGeocode(dto.geocode());

        addressRepo.save(address); // ✅ persist address changes
        partyAddressRepo.save(pa); // ✅ persist party-address changes

        return dto;
    }


    public void delete(PartyAddressId id) {
        partyAddressRepo.deleteById(id);
    }
}
