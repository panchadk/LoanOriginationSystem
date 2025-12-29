package com.adminplatform.los_auth.party.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "address_id", nullable = false)
    private UUID addressId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "line1", nullable = false)
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "city")
    private String city;

    @Column(name = "province_code")
    private String provinceCode;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "geocode", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode geocode;

    public Address() {}

    // Getters and setters

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public JsonNode getGeocode() {
        return geocode;
    }

    public void setGeocode(JsonNode geocode) {
        this.geocode = geocode;
    }
}