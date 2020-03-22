ALTER TABLE users
    ADD COLUMN country_code varchar(10);
ALTER TABLE users
    ALTER COLUMN last_name DROP NOT NULL;
ALTER TABLE companies
    ALTER COLUMN code DROP NOT NULL;
