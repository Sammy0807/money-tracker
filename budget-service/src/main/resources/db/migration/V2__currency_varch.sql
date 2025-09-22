-- Make currency match the entity: VARCHAR(3) NOT NULL
ALTER TABLE budget.budgets
ALTER COLUMN currency VARCHAR(3) NOT NULL;
