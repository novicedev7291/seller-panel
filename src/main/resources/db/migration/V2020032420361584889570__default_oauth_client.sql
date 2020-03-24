INSERT INTO oauth_client_details
(client_id, resource_ids, client_secret, scope,
authorized_grant_types, access_token_validity,
refresh_token_validity, autoapprove) VALUES
('default_client', '', '$2a$10$K4v6XMs1PqzjRM3NCuTIZeyI2cjxkJjYL/FIgKQDp8eDBZKMtQGUS', 'read,write',
                'password,authorization_code,refresh_token', '43200', '86400', '1');