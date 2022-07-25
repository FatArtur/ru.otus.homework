package ru.otus.dao;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceBuild;

import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao{

    private static DBServiceClient dbServiceClient;

    public ClientDaoImpl() {
        DbServiceBuild dbServiceBuild = new DbServiceBuild();
        dbServiceClient = dbServiceBuild.build();
        fillClients();
    }

    @Override
    public Client saveClient(Client client) {
        return dbServiceClient.saveClient(client);
    }

    @Override
    public Optional<Client> getClient(long id) {
        return dbServiceClient.getClient(id);
    }

    @Override
    public List<Client> findAll() {
        return dbServiceClient.findAll();
    }

    public void fillClients() {
        var client = new Client(null, "Anton", new Address(null, "AnyStreet"), List.of(new Phone(null, "13-555-22"),
                new Phone(null, "14-666-333")));
        saveClient(new Client(null, "Vasya", new Address(null, "AnyStreet"), List.of(new Phone(null, "13-777-22"))));
        saveClient(new Client(null, "Ivan", new Address(null, "Pushkina"), List.of(new Phone(null, "13-222-22"))));
        saveClient(new Client(null, "Gosha", new Address(null, "Gagarina"), List.of(new Phone(null, "13-111-11"))));
        saveClient(client);
    }
}
