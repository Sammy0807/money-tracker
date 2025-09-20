IF EXISTS (SELECT * FROM sysobjects WHERE name='users' AND xtype='U')
BEGIN
  IF EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.users') AND name = 'created_at' AND system_type_id = 42) -- 42 = datetime2
  BEGIN
    ALTER TABLE dbo.users ALTER COLUMN created_at DATETIMEOFFSET(6) NOT NULL;
  END

  IF EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.users') AND name = 'updated_at' AND system_type_id = 42)
  BEGIN
    ALTER TABLE dbo.users ALTER COLUMN updated_at DATETIMEOFFSET(6) NOT NULL;
  END
END;
