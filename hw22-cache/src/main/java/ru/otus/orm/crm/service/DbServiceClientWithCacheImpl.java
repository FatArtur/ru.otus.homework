package ru.otus.orm.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.cachehw.HwCache;
import ru.otus.orm.core.repository.DataTemplate;
import ru.otus.orm.core.sessionmanager.TransactionManager;
import ru.otus.orm.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientWithCacheImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientWithCacheImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> cache;

    public DbServiceClientWithCacheImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, HwCache<String, Client> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            if (client.getId() == null) {
                clientDataTemplate.insert(session, client);
                log.info("created client: {}", client);
                cache.put(String.valueOf(client.getId()), client);
                return client;
            }
            clientDataTemplate.update(session, client);
            log.info("updated client: {}", client);
            cache.put(String.valueOf(client.getId()), client);
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        var resultInCache = cache.get(String.valueOf(id));
        if (resultInCache != null) {
            return Optional.of(resultInCache);
        }
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            clientOptional.ifPresent(it -> cache.put(String.valueOf(id), it));
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
