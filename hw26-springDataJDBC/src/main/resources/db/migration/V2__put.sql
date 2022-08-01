insert into client(id, name) values (55, 'Vova');
insert into client(id, name) values (56, 'Ivan');
insert into address(id, street, client_id) values (78, 'Gagarina str', 55);
insert into address(id, street, client_id) values (44, 'Pushkina str', 56);
insert into phone(id, number, client_id) values (45, '111-111-11', 55);
insert into phone(id, number, client_id) values (67, '222-222-22', 56);