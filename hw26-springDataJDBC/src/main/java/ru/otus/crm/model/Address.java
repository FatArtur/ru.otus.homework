package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table(name = "address")
public class Address {
    @Id
    private Long id;
    @Nonnull
    private String street;
    @Nonnull
    private Long clientId;

    @PersistenceCreator
    public Address(Long id, @Nonnull String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getStreet() {
        return street;
    }

    public void setStreet(@Nonnull String street) {
        this.street = street;
    }

    @Nonnull
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(@Nonnull Long clientId) {
        this.clientId = clientId;
    }
}
