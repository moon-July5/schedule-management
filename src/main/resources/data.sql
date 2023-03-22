
insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (now(),now(),null,false, '김고수','$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.','admin', '', '마케팅','부장',15, false);
​
insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
values (1,'ROLE_ADMIN');
​
insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (now(),now(),null,false, '이미나','$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.','user1', 'abc@asdf.com', '마케팅','과장',7, false);
​
insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
values (2,'ROLE_USER');
​
insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (now(),now(),null,false, '박은우','$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.','user2', 'dkfk@asdf.com', '디자인','대리',5, false);
​
insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
values (3,'ROLE_USER');
​
insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (now(),now(),null,false, '유지현','','', 'yuu@axd.com', '회계','사원',7, false);
​
insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
values (4,'ROLE_USER');
​
insert into ACCOUNT (CREATED_AT, MODIFIED_AT, DELETED_AT, IS_DELETED, NAME, PASSWORD, ACCOUNT_ID, EMAIL, DEPARTMENT, POSITION, YEARLY, DUTY)
VALUES (now(),now(),null,false, '민시후','','', 'mmm@dfgf.com', '인사','사원',3, false);
​
insert into ACCOUNT_ROLES (ACCOUNT_ID,ROLES)
values (5,'ROLE_USER');