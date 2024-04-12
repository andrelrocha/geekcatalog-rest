--TEM QUE CRIAR UM NOVO SCHEMA PARA QUE O MESMO BANCO TENHA DIFERENTES SCHEMAS

--CREATE SCHEMA legacyapp; || ALTER SCHEMA public RENAME TO legacyapp;

--tem que adicionar o path de C:\Program Files\PostgreSQL\16\bin para a variável de ambiente "path"

--PS C:\Program Files\PostgreSQL\16\bin>

--extrai o schema legacyapp de backlogappadmin para criar "schema_public_dump.sql"
--pg_dump --host 127.0.0.1 --port 5432 --username andrerocha --format custom --verbose --schema=legacyapp --dbname=backlogappadmin > backlogappadmin_dump.backup


--pg_restore --host 127.0.0.1 --port 5432 --username andrerocha --dbname=backlogapp --schema=legacyapp .\backlogappadmin_dump.backup
--psql --host 127.0.0.1 --port 5432 --username andrerocha --dbname=backlogapp -f backlogappadmin_dump.backup
