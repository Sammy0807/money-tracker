-- If your V1 added defaults, drop them first (ignore if they don't exist)
BEGIN TRY ALTER TABLE dbo.accounts DROP CONSTRAINT DF_accounts_created; END TRY BEGIN CATCH END CATCH;
BEGIN TRY ALTER TABLE dbo.accounts DROP CONSTRAINT DF_accounts_updated; END TRY BEGIN CATCH END CATCH;

ALTER TABLE dbo.accounts
  ALTER COLUMN created_at datetimeoffset(6) NOT NULL;

ALTER TABLE dbo.accounts
  ALTER COLUMN updated_at datetimeoffset(6) NOT NULL;

ALTER TABLE dbo.accounts
  ADD CONSTRAINT DF_accounts_created DEFAULT (SYSUTCDATETIME() AT TIME ZONE 'UTC') FOR created_at;

ALTER TABLE dbo.accounts
  ADD CONSTRAINT DF_accounts_updated DEFAULT (SYSUTCDATETIME() AT TIME ZONE 'UTC') FOR updated_at;
GO