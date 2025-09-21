-- Add user_id column if missing
IF NOT EXISTS (
  SELECT 1
  FROM sys.columns
  WHERE Name = N'user_id'
    AND Object_ID = Object_ID(N'dbo.transactions')
)
BEGIN
  ALTER TABLE dbo.transactions
  ADD user_id uniqueidentifier NULL;
END