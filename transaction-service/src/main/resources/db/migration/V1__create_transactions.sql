-- Idempotent initial schema for transaction-service (transactions table, defaults, FKs, indexes)

-- CREATE table if missing
IF OBJECT_ID('dbo.transactions', 'U') IS NULL
BEGIN
  CREATE TABLE dbo.transactions (
    id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    account_id UNIQUEIDENTIFIER NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    type NVARCHAR(40) NOT NULL,
    description NVARCHAR(1000) NULL,
    amount DECIMAL(19,4) NOT NULL CONSTRAINT DF_transactions_amount DEFAULT (0),
    currency NVARCHAR(10) NOT NULL CONSTRAINT DF_transactions_currency DEFAULT ('USD'),
    external_ref NVARCHAR(200) NULL,
    occurred_at DATETIMEOFFSET(6) NOT NULL CONSTRAINT DF_transactions_occurred_at DEFAULT SYSUTCDATETIME(),
    created_at DATETIMEOFFSET(6) NOT NULL CONSTRAINT DF_transactions_created DEFAULT SYSUTCDATETIME(),
    updated_at DATETIMEOFFSET(6) NOT NULL CONSTRAINT DF_transactions_updated DEFAULT SYSUTCDATETIME(),
    is_archived BIT NOT NULL CONSTRAINT DF_transactions_archived DEFAULT 0
  );

  CREATE INDEX IX_transactions_account_occurred ON dbo.transactions(account_id, occurred_at);
  CREATE INDEX IX_transactions_user_occurred ON dbo.transactions(user_id, occurred_at);
END
GO

-- If table exists, add any missing columns (safe to re-run)
IF OBJECT_ID('dbo.transactions','U') IS NOT NULL
BEGIN
  IF COL_LENGTH('dbo.transactions', 'amount') IS NULL
  BEGIN
    ALTER TABLE dbo.transactions ADD amount DECIMAL(19,4) NOT NULL CONSTRAINT DF_transactions_amount DEFAULT (0);
  END

  IF NOT EXISTS (
    SELECT 1
    FROM sys.columns
    WHERE Name = N'occurred_at'
      AND Object_ID = Object_ID(N'dbo.transactions')
  )
  BEGIN
    ALTER TABLE dbo.transactions
    ADD occurred_at datetime2(3) NOT NULL
      CONSTRAINT DF_transactions_occurred_at DEFAULT SYSUTCDATETIME();
  END

  IF COL_LENGTH('dbo.transactions', 'created_at') IS NULL
  BEGIN
    ALTER TABLE dbo.transactions ADD created_at DATETIMEOFFSET(6) NOT NULL CONSTRAINT DF_transactions_created DEFAULT SYSUTCDATETIME();
  END

  IF COL_LENGTH('dbo.transactions', 'updated_at') IS NULL
  BEGIN
    ALTER TABLE dbo.transactions ADD updated_at DATETIMEOFFSET(6) NOT NULL CONSTRAINT DF_transactions_updated DEFAULT SYSUTCDATETIME();
  END

  IF COL_LENGTH('dbo.transactions', 'currency') IS NULL
  BEGIN
    ALTER TABLE dbo.transactions ADD currency NVARCHAR(10) NOT NULL CONSTRAINT DF_transactions_currency DEFAULT ('USD');
  END

  IF COL_LENGTH('dbo.transactions', 'is_archived') IS NULL
  BEGIN
    ALTER TABLE dbo.transactions ADD is_archived BIT NOT NULL CONSTRAINT DF_transactions_archived DEFAULT 0;
  END
END
GO

-- Add FK to accounts only if accounts table exists and FK not present
IF OBJECT_ID('dbo.accounts','U') IS NOT NULL
BEGIN
  -- add FK only if not already present
  IF NOT EXISTS (
    SELECT 1 FROM sys.foreign_keys fk
    JOIN sys.tables t ON fk.parent_object_id = t.object_id
    WHERE t.name = 'transactions' AND fk.name = 'FK_transactions_accounts'
  )
  BEGIN
    ALTER TABLE dbo.transactions
      ADD CONSTRAINT FK_transactions_accounts FOREIGN KEY (account_id) REFERENCES dbo.accounts(id);
  END
END
GO