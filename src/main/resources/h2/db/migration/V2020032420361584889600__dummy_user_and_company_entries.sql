insert into companies (id, name, code, description, active, created_on, updated_on) values (1, 'Default Company', 'DEF',
'Default Company', true, '2020-03-14', '2020-03-14');

insert into users (id, name, email, phone, country_code, password, active, company_id, created_on, updated_on) values
(1, 'Dummy', 'dummy-seller-panel1@gmail.com', null, null, '$2a$10$701uMTqsFG1kfL/ymQGd3.ii0j55jnnW6d5dkJLuEAB6SZ2UfyhfO', true, 1, '2020-03-14', '2020-03-14');

INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity, autoapprove) VALUES
('sp-test-client-id', '', '$2a$10$701uMTqsFG1kfL/ymQGd3.ii0j55jnnW6d5dkJLuEAB6SZ2UfyhfO', 'read,write', 'password,authorization_code,refresh_token', '43200', '86400', '1');