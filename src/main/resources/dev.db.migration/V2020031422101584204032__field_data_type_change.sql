ALTER TABLE oauth_access_token
  ALTER COLUMN token TYPE varchar,
  ALTER COLUMN authentication TYPE varchar,
  ALTER COLUMN refresh_token TYPE varchar;

ALTER TABLE oauth_refresh_token
  ALTER COLUMN token TYPE varchar,
  ALTER COLUMN authentication TYPE varchar;

ALTER TABLE oauth_code
  ALTER COLUMN authentication TYPE varchar;