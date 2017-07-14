DROP TABLE IF EXISTS online_app_system.courses;
DROP TABLE IF EXISTS online_app_system.sent_emails;
DROP TABLE IF EXISTS online_app_system.application_screening_info;
DROP TABLE IF EXISTS online_app_system.test_results;
DROP TABLE IF EXISTS online_app_system.tests;
DROP TABLE IF EXISTS online_app_system.applications;
DROP TABLE IF EXISTS online_app_system.location_types;
DROP TABLE IF EXISTS online_app_system.test_answers;
DROP TABLE IF EXISTS online_app_system.authentication;
DROP TABLE IF EXISTS online_app_system.personal_data;
DROP TABLE IF EXISTS online_app_system.system_user;

CREATE TABLE online_app_system.system_user
(
  id VARCHAR(40) PRIMARY KEY NOT NULL,
  auth0user_id VARCHAR (80),
  admin_id SERIAL,
  given_name VARCHAR (255),
  family_name VARCHAR (255),
  middle_name VARCHAR (255),
  registered_at VARCHAR (255),
  gender VARCHAR (255),
  birth_date INT,
  photo_url VARCHAR (255),
  phone_number VARCHAR (255),
  location_id VARCHAR (255),
  user_hash VARCHAR (80),
  adminuuid INT,
  is_blacklisted BOOLEAN,
  can_apply boolean,
  gmail_account boolean
);

create table online_app_system.applications
(
  id VARCHAR(40) PRIMARY KEY NOT NULL,
  applicant_id VARCHAR(40) REFERENCES online_app_system.system_user(id),
  course_id INT,
  location_id varchar(40),
  process_started_at TIMESTAMP,
  final_result boolean,
  contract_signed boolean,
  active BOOLEAN,
  status VARCHAR(200),
  utm_source VARCHAR(255),
  utm_medium VARCHAR(255),
  utm_campaign VARCHAR(255)
);

create table online_app_system.sent_emails
(
  id VARCHAR(40) PRIMARY KEY NOT NULL,
  application_id VARCHAR(40) REFERENCES online_app_system.applications(id),
  initial VARCHAR(255),
  info VARCHAR(255),
  reminder VARCHAR(255),
  deadline_reminder VARCHAR(255),
  schedule_or_failed VARCHAR(255),
  schedule_reminder VARCHAR(255),
  screening_thanks VARCHAR(255)
);
create table online_app_system.application_screening_info
(
  id VARCHAR(40) PRIMARY KEY NOT NULL,
  application_id VARCHAR(40) REFERENCES online_app_system.applications(id),
  screening_day VARCHAR(255),
  screening_group_time VARCHAR(255),
  screening_personal_time VARCHAR(255),
  schedule_signed_back boolean,
  map_location VARCHAR (255)
);
create table online_app_system.location_types
(
  id VARCHAR(40) PRIMARY KEY NOT NULL UNIQUE,
  name VARCHAR(255),
  course_type VARCHAR(255)
);
create table online_app_system.tests
(
  id VARCHAR(40) PRIMARY KEY NOT NULL,
  location_id VARCHAR(40) REFERENCES online_app_system.location_types(id),
  name VARCHAR(255),
  form_url VARCHAR(255),
  max_points INT,
  threshold INT,
  enabled boolean,
  order_in_bundle INT,
  estimated_time INT,
  motivation_video boolean,
  form_as_json TEXT,
  description TEXT
);

create table online_app_system.test_results
(
  id VARCHAR(40) PRIMARY KEY NOT NULL,
  application_id VARCHAR(40) REFERENCES online_app_system.applications(id),
  test_id VARCHAR(40) REFERENCES online_app_system.tests(id),
  started TIMESTAMP ,
  finished TIMESTAMP ,
  points INT,
  percent DOUBLE PRECISION,
  passed boolean,
  comment VARCHAR(255)
);


create table online_app_system.courses
(
  id INT PRIMARY KEY NOT NULL,
  location_id VARCHAR(40) REFERENCES online_app_system.location_types(id),
  name VARCHAR(255),
  open boolean,
  start_date TIMESTAMP,
  filled boolean,
  enabled boolean
);

create table online_app_system.test_answers (
  question_id VARCHAR(40) PRIMARY KEY NOT NULL,
  correct_answer VARCHAR(255)
);

create table online_app_system.authentication (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  token VARCHAR(255),
  refresh_token varchar(255),
  expires TIMESTAMP,
  auth_provider VARCHAR(50)
);


create table online_app_system.personal_data (
  user_id VARCHAR(255) PRIMARY KEY NOT NULL,
  name VARCHAR(255),
  maiden_name VARCHAR(255),
  address VARCHAR(255),
  gmail_account VARCHAR(255),
  mothers_name VARCHAR(255),
  place_of_birth VARCHAR(255),
  birth_date TIMESTAMP,
  id_document_type VARCHAR(255),
  id_number VARCHAR(255)
);

grant all PRIVILEGES ON schema online_app_system to applications;
grant all PRIVILEGES on ALL TABLES IN SCHEMA online_app_system to applications;

INSERT INTO online_app_system.location_types (id, name, course_type) VALUES ('BUD', 'Budapest, HU', 'GENERAL');
INSERT INTO online_app_system.location_types (id, name, course_type) VALUES ('KRK', 'Kraków, PL', 'GENERAL');
INSERT INTO online_app_system.location_types (id, name, course_type) VALUES ('MSC', 'Miskolc, HU', 'GENERAL');
INSERT INTO online_app_system.location_types (id, name, course_type) VALUES ('WRS', 'Warsaw, PL', 'GENERAL');
