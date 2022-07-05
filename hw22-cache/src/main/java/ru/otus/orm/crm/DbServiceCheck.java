package ru.otus.orm.crm;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.core.repository.DataTemplateHibernate;
import ru.otus.orm.core.repository.HibernateUtils;
import ru.otus.orm.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.orm.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.orm.crm.model.Address;
import ru.otus.orm.crm.model.Client;
import ru.otus.orm.crm.model.Phone;
import ru.otus.orm.crm.service.DBServiceClient;
import ru.otus.orm.crm.service.DbServiceClientImpl;
import ru.otus.orm.crm.service.DbServiceClientWithCacheImpl;

import java.util.List;

public class DbServiceCheck {

    private static final Logger log = LoggerFactory.getLogger(DbServiceCheck.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        var dbServiceClientCache = new DbServiceClientWithCacheImpl(transactionManager, clientTemplate);

        action(dbServiceClient);
        action(dbServiceClientCache);

    }

    public static void action(DBServiceClient dbServiceClient){
        long start1 = System.currentTimeMillis();
        dbServiceClient.saveClient(new Client(null, "Vasya", new Address(null, "AnyStreet"), List.of(new Phone(null, "13-555-22"),
                new Phone(null, "14-666-333"))));

        var clientSecond = dbServiceClient.saveClient(new Client(null, "Ivan", new Address(null, "Gagarina Street"), List.of(new Phone(null, "11-355-42"),
                new Phone(null, "12-111-633"))));
        dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        clientSecond.setName("OtherIvan");
        dbServiceClient.saveClient(clientSecond);
        long finish1 = System.currentTimeMillis();
        log.info("Result time:{} \n", finish1 - start1);
    }
}
