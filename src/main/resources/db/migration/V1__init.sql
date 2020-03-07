--
-- Table structure for table oauth_client_details
--
DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id varchar(255) NOT NULL,
  resource_ids varchar(255) DEFAULT NULL,
  client_secret varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  authorized_grant_types varchar(255) DEFAULT NULL,
  web_server_redirect_uri varchar(255) DEFAULT NULL,
  authorities varchar(255) DEFAULT NULL,
  access_token_validity int DEFAULT NULL,
  refresh_token_validity int DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  autoapprove varchar(255) DEFAULT NULL,
  PRIMARY KEY (client_id)
);

-- -----------------------------------------------------
-- Table structure for table oauth_client_token
-- -----------------------------------------------------
DROP TABLE IF EXISTS oauth_client_token;
CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id varchar(255) NOT NULL,
  token bytea NOT NULL,
  authentication_id varchar(255) DEFAULT NULL,
  user_name varchar(255) DEFAULT NULL,
  client_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (token_id)
);

-- -----------------------------------------------------
-- Table structure for table oauth_access_token
-- -----------------------------------------------------
DROP TABLE IF EXISTS oauth_access_token;
CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id varchar(255) NOT NULL,
  token bytea NOT NULL,
  authentication_id varchar(255) DEFAULT NULL,
  user_name varchar(255) DEFAULT NULL,
  client_id varchar(255) DEFAULT NULL,
  authentication bytea DEFAULT NULL,
  refresh_token bytea DEFAULT NULL,
  PRIMARY KEY (token_id)
);

-- -----------------------------------------------------
-- Table structure for table oauth_refresh_token
-- -----------------------------------------------------
DROP TABLE IF EXISTS oauth_refresh_token;
CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id varchar(255) NOT NULL,
  token bytea NOT NULL,
  authentication bytea DEFAULT NULL,
  PRIMARY KEY (token_id)
);

-- -----------------------------------------------------
-- Table structure for table oauth_code
-- -----------------------------------------------------
DROP TABLE IF EXISTS oauth_code;
CREATE TABLE IF NOT EXISTS oauth_code (
  code varchar(255) DEFAULT NULL,
  authentication bytea DEFAULT NULL
);

-- -----------------------------------------------------
-- Table structure for table oauth_approvals
-- -----------------------------------------------------
DROP TABLE IF EXISTS oauth_approvals;
CREATE TABLE IF NOT EXISTS oauth_approvals (
  expiresAt TIMESTAMP DEFAULT NULL,
  status varchar(255) DEFAULT NULL,
  lastModifiedAt TIMESTAMP DEFAULT NULL,
  userId varchar(255) DEFAULT NULL,
  clientId varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL
);

-- -----------------------------------------------------
-- Table companies
-- -----------------------------------------------------
DROP TABLE IF EXISTS companies;
CREATE TABLE IF NOT EXISTS companies (
  id BIGSERIAL NOT NULL,
  name VARCHAR(100) NOT NULL,
  code VARCHAR(20) NOT NULL,
  description VARCHAR(250) NULL,
  active BOOLEAN NULL DEFAULT false,
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id));
CREATE UNIQUE INDEX idx_companies_code ON companies(code);

-- -----------------------------------------------------
-- Table users
-- -----------------------------------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL NOT NULL,
  first_name VARCHAR(200) NOT NULL,
  last_name VARCHAR(200) NOT NULL,
  email VARCHAR(200) NOT NULL,
  phone VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  active BOOLEAN NULL DEFAULT true,
  company_id BIGINT NOT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table roles
-- -----------------------------------------------------
DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles (
  id BIGSERIAL NOT NULL,
  name VARCHAR(100) NOT NULL,
  company_id BIGINT NOT NULL,
  is_deletable BOOLEAN NULL DEFAULT true,
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id));
CREATE UNIQUE INDEX idx_roles_name ON roles(name);

-- -----------------------------------------------------
-- Table user_roles
-- -----------------------------------------------------
DROP TABLE IF EXISTS user_roles;
CREATE TABLE IF NOT EXISTS user_roles (
  id BIGSERIAL NOT NULL,
  role_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_user_roles_role_id
    FOREIGN KEY (role_id)
    REFERENCES roles (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_user_roles_user_id
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
-- -----------------------------------------------------
-- Table permissions
-- -----------------------------------------------------
DROP TABLE IF EXISTS permissions;
CREATE TABLE IF NOT EXISTS permissions (
  id BIGSERIAL NOT NULL,
  name VARCHAR(50) NOT NULL,
  code VARCHAR(45) NOT NULL,
  description VARCHAR(200) NOT NULL,
  verb VARCHAR(10) NOT NULL,
  is_deletable BOOLEAN NULL DEFAULT true,
  link VARCHAR(200) NOT NULL,
  role_id INT NOT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id));

  CREATE UNIQUE INDEX idx_permissions_company_id_name ON permissions(name);

-- -----------------------------------------------------
-- Table user_roles
-- -----------------------------------------------------
DROP TABLE IF EXISTS role_permissions;
CREATE TABLE IF NOT EXISTS role_permissions (
  id BIGSERIAL NOT NULL,
  role_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_role_permissions_role_id
    FOREIGN KEY (role_id)
    REFERENCES roles (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_role_permissions_permission_id
    FOREIGN KEY (permission_id)
    REFERENCES permissions (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);