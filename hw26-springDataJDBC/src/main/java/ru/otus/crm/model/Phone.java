package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;


@Table(name = "phone")
public class Phone {
    @Id
    private Long id;

    @Nonnull
    private String number;

    @Nonnull
    private Long clientId;

    @PersistenceCreator
    public Phone(Long id, @Nonnull String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getNumber() {
        return number;
    }

    public void setNumber(@Nonnull String number) {
        this.number = number;
    }

    @Nonnull
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(@Nonnull Long clientId) {
        this.clientId = clientId;
    }
}
