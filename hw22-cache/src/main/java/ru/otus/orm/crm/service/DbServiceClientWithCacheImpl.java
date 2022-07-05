package ru.otus.orm.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.core.repository.DataTemplate;
import ru.otus.orm.core.sessionmanager.TransactionManager;
import ru.otus.orm.crm.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.WeakHashMap;

public class DbServiceClientWithCacheImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientWithCacheImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final WeakHashMap<String, Client> cache;

    public DbServiceClientWithCacheImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = new WeakHashMap<>();
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
//            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, client);
                log.info("created client: {}", client);
                cache.put(String.valueOf(client.getId()), client);
                return client;
            }
            clientDataTemplate.update(session, client);
            log.info("updated client: {}", client);
            if (cache.get(String.valueOf(client.getId())) != null) {
                cache.replace(String.valueOf(client.getId()), client);
            } else {
                cache.put(String.valueOf(client.getId()), client);
            }
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var resultInCache = cache.get(String.valueOf(id));
            if (resultInCache != null) {
                return Optional.of(resultInCache);
            }
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }
}
