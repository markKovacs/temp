create user applications password 'password';
create schema online_app_system AUTHORIZATION applications;
GRANT ALL ON SCHEMA online_app_system TO applications;
grant all privileges on ALL TABLES
IN SCHEMA online_app_system TO applications;
GRANT CREATE ON DATABASE onlineappsystem TO applications;