insert into account (id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (1, now(),now(),null,false, '66KNJxQuvktGB4W2swshwQ==','$2a$10$eabjAFIFCj9u6nqA9mJL0O7yIBISaEb0ZtDo.3g3UQBPGix1PWiui','jMhAHd/tL3plV8asm5BrVw==', 'ObiM4MMvrtPBLUsKOLKRpQ==', 'vTkDe3p59kS0E0jaUv9h8Q==','Y9eJz3F996LveX7sZculuA==',15, true);

insert into account_roles (ACCOUNT_ID,ROLES)
values (1,'ROLE_ADMIN');

insert into account (id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (2, now(),now(),null,false, 'BOy243RVJBJY3jaH2PGb8w==','$2a$10$eabjAFIFCj9u6nqA9mJL0O7yIBISaEb0ZtDo.3g3UQBPGix1PWiui','9cdUlJvRyzUGlf09hCQQRg==', '7ka0XswutP+8zs5O3jiHCQ==', 'vTkDe3p59kS0E0jaUv9h8Q==','p7+H3xZk3+qtymf9Bwl+Tg==',7, true);

insert into account_roles (ACCOUNT_ID,ROLES)
values (2,'ROLE_USER');

insert into account (id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (3, now(),now(),null,false, 'otmdHXusOMNyJmOX5eV6mw==','$2a$10$eabjAFIFCj9u6nqA9mJL0O7yIBISaEb0ZtDo.3g3UQBPGix1PWiui','Ln02Pz/an6e503b9rLxQVA==', 'txWocglGvpSkvWajAlg8Gg==', 'e9sbZK9Grag43l8xi+EJEA==','T8sAGMymR4WJibqWe1UL+g==',5, false);

insert into account_roles (ACCOUNT_ID,ROLES)
values (3,'ROLE_USER');

insert into account (id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME , EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (4, now(),now(),null,false, '9JlJCVjY+DEiZyPUVO7cxw==', 'KMtv//hpz+c33L10ev96Aw==', 'ddj1nxycaldW2VDcF/dR+Q==','N/WRncJTYTwzcA72M+KdaQ==',7, false);

insert into account_roles (ACCOUNT_ID,ROLES)
values (4,'ROLE_USER');

insert into account (id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME,  EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (5, now(),now(),null,false, 'OOAfJO/+5kSkh8hOCjtD9Q==','XMP7kp2U3XQKxNRmWVKwIQ==', '20ffY5+QOBw6xRPoYl0n6w==','N/WRncJTYTwzcA72M+KdaQ==',3, false);

insert into account_roles (ACCOUNT_ID,ROLES)
values (5,'ROLE_USER');

insert into schedule(id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, start_date, end_date, type, content, account_id)
values (1, now(), now(), null, false, '2023-03-29 18:00:00', '2023-03-30 06:00:00', 'DUTY', null, 2);

insert into schedule(id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, start_date, end_date, type, content, account_id)
values (2, now(), now(), null, false, '2023-04-04 18:00:00', '2023-04-05 06:00:00', 'DUTY', null, 2);

insert into schedule(id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, start_date, end_date, type, content, account_id)
values (3, now(), now(), null, false, '2023-04-06 18:00:00', '2023-04-07 06:00:00', 'DUTY', null, 3);

insert into schedule(id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, start_date, end_date, type, content, account_id)
values (4, now(), now(), null, false, '2023-03-29 00:00:00', '2023-03-31 00:00:00', 'YEARLY', null, 3);

insert into schedule(id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, start_date, end_date, type, content, account_id)
values (5, now(), now(), null, false, '2023-04-10 00:00:00', '2023-04-11 00:00:00', 'YEARLY', null, 4);

insert into schedule(id, CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, start_date, end_date, type, content, account_id)
values (6, now(), now(), null, false, '2023-04-18 00:00:00', '2023-04-20 00:00:00', 'PLAN', 'design meeting', 3);






-- insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
-- VALUES (now(),now(),null,false, '김고수','$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.','admin', '', '마케팅','부장',15, false);
--
-- insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
-- values (1,'ROLE_ADMIN');
--
-- insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
-- VALUES (now(),now(),null,false, '이미나','$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.','user1', 'abc@asdf.com', '마케팅','과장',7, false);
--
-- insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
-- values (2,'ROLE_USER');
--
-- insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
-- VALUES (now(),now(),null,false, '박은우','$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.','user2', 'dkfk@asdf.com', '디자인','대리',5, false);
--
-- insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
-- values (3,'ROLE_USER');
--
-- insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
-- VALUES (now(),now(),null,false, '유지현','','', 'yuu@axd.com', '회계','사원',7, false);
--
-- insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
-- values (4,'ROLE_USER');
--
-- insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
-- VALUES (now(),now(),null,false, '민시후','','', 'mmm@dfgf.com', '인사','사원',3, false);
--
-- insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
-- values (5,'ROLE_USER');
