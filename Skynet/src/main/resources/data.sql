insert into user (dtype, id, username, password, name, surname, email, enabled,last_password_reset_date, first_time)
values('User',1,'admin','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','Stojko', 'Stojanovic', 'stojko@example.com', true, '2019-04-28 21:00:00', true);
insert into authority (id, name) values (1, 'ROLE_SYSTEM_ADMIN');
insert into user_authority (user_id, authority_id) values(1,1);