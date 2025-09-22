CREATE TABLE IF NOT EXISTS transaction_fact (
  id             UUID PRIMARY KEY,
  user_id        UUID NOT NULL,
  category       VARCHAR(50) NOT NULL,
  amount         NUMERIC(19,4) NOT NULL,
  currency       CHAR(3) NOT NULL,
  occurred_at    TIMESTAMPTZ NOT NULL,
  created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_transaction_fact_user_time
  ON transaction_fact (user_id, occurred_at);

CREATE INDEX IF NOT EXISTS idx_transaction_fact_category
  ON transaction_fact (category);
