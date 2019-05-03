insert into user (dtype, id, username, password, name, surname, email, enabled,last_password_reset_date, first_time)
values('User',1,'admin','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','Stojko', 'Stojanovic', 'stojko@example.com', true, '2019-04-28 21:00:00', true);
insert into authority (id, name) values (1, 'ROLE_SYSTEM_ADMIN');
insert into user_authority (user_id, authority_id) values(1,1);
insert into airline (id,name,address, description, image ) values (1, 'JAT','Batajnica', 'Low cost', 'images/airline1.jpg');
insert into user (email, enabled, first_time, last_password_reset_date, name, password, surname, username, airline_id, dtype, id) values ('bobic@gmail.com', 1, 1, '2019-04-28 21:00:00', 'Bob', '$2a$10$fNx6Gfz./Mk78B2Kc..4SePYki.PbEOt8ahwjEv4/zUO6qFaxeU7K', 'Bobic', 'avion', 1, 'AirlineAdmin', 2);
insert into authority (id, name) values (2, 'ROLE_AIRLINE_ADMIN');
insert into user_authority (user_id, authority_id) values(2,2);