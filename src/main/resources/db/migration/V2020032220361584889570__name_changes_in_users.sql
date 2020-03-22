alter table users RENAME to old_users;
CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL NOT NULL,
  name VARCHAR(400) NOT NULL,
  email VARCHAR(200) NOT NULL,
  phone VARCHAR(50) NULL,
  country_code varchar(10),
  password VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT true,
  company_id BIGINT NOT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id));
INSERT INTO users (id, name, email, phone, country_code, password, active, company_id, created_on, updated_on)
                    select id, first_name, email, phone, country_code, password, active, company_id, created_on,
                    updated_on from old_users;
ALTER TABLE user_roles DROP CONSTRAINT fk_user_roles_user_id;
ALTER TABLE user_roles ADD CONSTRAINT fk_user_roles_user_id
                               FOREIGN KEY (user_id)
                               REFERENCES users (id)
                               ON DELETE NO ACTION
                               ON UPDATE NO ACTION;
DROP TABLE IF EXISTS old_users;
