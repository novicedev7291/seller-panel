-- -----------------------------------------------------
-- Table user_roles
-- -----------------------------------------------------
DROP TABLE IF EXISTS categories;
CREATE TABLE IF NOT EXISTS categories (
  id BIGSERIAL NOT NULL,
  company_id BIGINT NOT NULL,
  parent_id BIGINT,
  name VARCHAR(400) NOT NULL,
  description VARCHAR(400),
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_categories_company_id
    FOREIGN KEY (company_id)
    REFERENCES companies (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_categories_parent_id
    FOREIGN KEY (parent_id)
    REFERENCES categories (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);