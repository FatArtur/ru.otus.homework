create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table address
(
    id   bigserial not null primary key,
    street varchar(50),
    client_id bigint
);

alter table address
    add constraint address_fk
        foreign key (client_id)
            references client;

create table phone
(
    id   bigserial not null primary key,
    number varchar(50),
    client_id bigint
);

alter table phone
    add constraint phone_fk
        foreign key (client_id)
            references client;