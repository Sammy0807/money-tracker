-- Add 'type' column if missing (idempotent)
IF NOT EXISTS (
  SELECT 1
  FROM sys.columns
  WHERE Name = N'type'
    AND Object_ID = Object_ID(N'dbo.transactions')
)
BEGIN
  ALTER TABLE dbo.transactions
  ADD [type] nvarchar(50) NOT NULL
    CONSTRAINT DF_transactions_type DEFAULT N'UNKNOWN';
END