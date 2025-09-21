-- Add default constraint for posted_at if missing (idempotent)
IF NOT EXISTS (
  SELECT 1
  FROM sys.default_constraints dc
  JOIN sys.columns c ON dc.parent_object_id = c.object_id AND dc.parent_column_id = c.column_id
  WHERE c.name = N'posted_at' AND OBJECT_NAME(dc.parent_object_id) = N'transactions'
)
BEGIN
  ALTER TABLE dbo.transactions
  ADD CONSTRAINT DF_transactions_posted_at
    DEFAULT SYSUTCDATETIME() FOR posted_at;
END