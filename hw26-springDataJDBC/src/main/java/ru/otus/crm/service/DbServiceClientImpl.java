package ru.otus.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.core.repository.AddressRepository;
import ru.otus.core.repository.ClientRepository;
import ru.otus.core.repository.PhoneRepository;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.sessionmanager.TransactionManager;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;
    private final TransactionManager transactionManager;

    @Override
    public Client saveClient(ClientDto client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = deserializeClient(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        var clientOptional = clientRepository.findById(id);
        log.info("client: {}", clientOptional);
        return clientOptional;
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("clientList:{}", clientList);
        return clientList;
    }

    private Client deserializeClient(ClientDto clientDto) {
        var client = clientRepository.save(new Client(null, clientDto.getName()));
        var address = addressRepository.save(new Address(null, clientDto.getAddress(), client.getId()));
        var phones = clientDto.getPhone();
        Set<Phone> phoneList = new HashSet<>();
        phoneList.add(phoneRepository.save(new Phone(null, phones, client.getId())));
        client.setAddress(address);
        client.setPhones(phoneList);
        return client;
    }
}
