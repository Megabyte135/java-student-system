IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'StudentFirstLab')
BEGIN
    CREATE DATABASE StudentFirstLab;
END