-- ...existing code...
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
-- ...existing code...