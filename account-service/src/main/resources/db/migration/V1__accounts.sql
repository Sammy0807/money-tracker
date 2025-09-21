IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='accounts' AND xtype='U')
BEGIN
  CREATE TABLE dbo.accounts (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    user_id UNIQUEIDENTIFIER NOT NULL,
    name NVARCHAR(100) NOT NULL,
    institution NVARCHAR(120) NULL,
    type NVARCHAR(40) NOT NULL,            -- CHECKING/SAVINGS/CREDIT/BROKERAGE/CASH
    currency NVARCHAR(10) NOT NULL,
    balance DECIMAL(18,2) NOT NULL CONSTRAINT DF_accounts_balance DEFAULT 0,
    external_ref NVARCHAR(200) NULL,       -- id from bank aggregator (optional)
    is_archived BIT NOT NULL CONSTRAINT DF_accounts_archived DEFAULT 0,
    created_at DATETIME2(6) NOT NULL CONSTRAINT DF_accounts_created DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2(6) NOT NULL CONSTRAINT DF_accounts_updated DEFAULT SYSUTCDATETIME()
  );

  -- Uniqueness per user to avoid dupes when re-importing
  CREATE UNIQUE INDEX UX_accounts_user_name ON dbo.accounts(user_id, name) WHERE is_archived = 0;
  CREATE UNIQUE INDEX UX_accounts_user_ext  ON dbo.accounts(user_id, external_ref) WHERE external_ref IS NOT NULL;

  -- FK to users table (same DB)
  ALTER TABLE dbo.accounts ADD CONSTRAINT FK_accounts_users
    FOREIGN KEY (user_id) REFERENCES dbo.users(id);
END;
