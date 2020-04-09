-- -----------------------------------------------------
-- Table skus
-- -----------------------------------------------------
DROP TABLE IF EXISTS skus;
CREATE TABLE IF NOT EXISTS skus (
  id BIGSERIAL NOT NULL,
  company_id BIGINT NOT NULL,
  categories ARRAY NULL,
  type smallint NOT NULL,
  code VARCHAR(100) NOT NULL,
  name VARCHAR(400) NOT NULL,
  title VARCHAR(400) NOT NULL,
  description VARCHAR(400),
  base_uom VARCHAR(50) NOT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_skus_company_id
    FOREIGN KEY (company_id)
    REFERENCES companies (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);