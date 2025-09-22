/* Create schema if missing */
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'budget')
    EXEC('CREATE SCHEMA budget');

/* Drop existing table (and its indexes/constraints) if it exists */
IF OBJECT_ID('budget.budgets', 'U') IS NOT NULL
    DROP TABLE budget.budgets;

/* Recreate table to match the entity exactly */
CREATE TABLE budget.budgets (
    id            UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    user_id       UNIQUEIDENTIFIER NOT NULL,
    account_id    UNIQUEIDENTIFIER NULL,
    category      NVARCHAR(50)     NOT NULL,
    period_start  DATE             NOT NULL,
    period_end    DATE             NOT NULL,
    amount        DECIMAL(19,4)    NOT NULL,
    currency      NCHAR(3)         NOT NULL,
    created_at    DATETIME2        NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at    DATETIME2        NULL,
    CONSTRAINT ck_budgets_period CHECK (period_start <= period_end)
);

/* Create indexes if missing */
IF NOT EXISTS (
    SELECT 1 FROM sys.indexes
    WHERE name = 'ix_budgets_user'
      AND object_id = OBJECT_ID('budget.budgets')
)
CREATE INDEX ix_budgets_user
  ON budget.budgets (user_id, period_start, period_end);

IF NOT EXISTS (
    SELECT 1 FROM sys.indexes
    WHERE name = 'ix_budgets_account'
      AND object_id = OBJECT_ID('budget.budgets')
)
CREATE INDEX ix_budgets_account
  ON budget.budgets (account_id);
