insert into companies (id, name, code, description, active, created_on, updated_on) values (1, 'Default Company', 'DEF',
'Default Company', true, '2020-03-14', '2020-03-14');

insert into users (id, first_name, last_name, email, phone, password, active, company_id, created_on, updated_on) values
(1, 'Dummy', 'Seller', 'dummy-seller-panel1@gmail.com', null, 'Passw@rd', true, 1, '2020-03-14', '2020-03-14');