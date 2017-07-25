DROP TABLE IF EXISTS online_app_system.courses;
DROP TABLE IF EXISTS online_app_system.sent_emails;
DROP TABLE IF EXISTS online_app_system.application_screening_info;
-- DROP TABLE IF EXISTS online_app_system.test_results;
DROP TABLE IF EXISTS online_app_system.tests;
DROP TABLE IF EXISTS online_app_system.applications;
DROP TABLE IF EXISTS online_app_system.location_types;
DROP TABLE IF EXISTS online_app_system.test_answers;
DROP TABLE IF EXISTS online_app_system.authentication;
DROP TABLE IF EXISTS online_app_system.personal_data;
DROP TABLE IF EXISTS online_app_system.system_user;

CREATE SEQUENCE system_user_admin_id_seq START 101;

CREATE TABLE online_app_system.system_user
(
  id            VARCHAR(40) PRIMARY KEY NOT NULL,
  auth0user_id  VARCHAR(80),
  given_name    VARCHAR(255),
  family_name   VARCHAR(255),
  middle_name   VARCHAR(255),
  registered_at TIMESTAMP,
  gender        VARCHAR(255),
  birth_date    INT,
  photo_url     VARCHAR(255),
  phone_number  VARCHAR(255),
  location_id   VARCHAR(255),
  user_hash     VARCHAR(80),
  admin_id INTEGER,
  can_apply     BOOLEAN,
  is_blacklisted BOOLEAN,
  gmail_account BOOLEAN
);

CREATE TABLE online_app_system.applications
(
  id                 VARCHAR(40) PRIMARY KEY NOT NULL,
  applicant_id       VARCHAR(40) REFERENCES online_app_system.system_user (id),
  course_id          INT,
  location_id        VARCHAR(40),
  process_started_at TIMESTAMP,
  final_result       BOOLEAN,
  contract_signed    BOOLEAN,
  active             BOOLEAN,
  utm_source         VARCHAR(255),
  utm_medium         VARCHAR(255),
  utm_campaign       VARCHAR(255),
  comment            VARCHAR(255)
);

CREATE TABLE online_app_system.sent_emails
(
  id                 VARCHAR(40) PRIMARY KEY NOT NULL,
  application_id     VARCHAR(40) REFERENCES online_app_system.applications (id),
  initial            VARCHAR(255),
  info               VARCHAR(255),
  reminder           VARCHAR(255),
  deadline_reminder  VARCHAR(255),
  schedule_or_failed VARCHAR(255),
  schedule_reminder  VARCHAR(255),
  screening_thanks   VARCHAR(255)
);
CREATE TABLE online_app_system.application_screening_info
(
  id                      VARCHAR(40) PRIMARY KEY NOT NULL,
  application_id          VARCHAR(40) REFERENCES online_app_system.applications (id),
  screening_day           VARCHAR(255),
  screening_group_time    TIMESTAMP,
  screening_personal_time TIMESTAMP,
  schedule_signed_back    BOOLEAN,
  map_location            VARCHAR(255)
);
CREATE TABLE online_app_system.location_types
(
  id          VARCHAR(40) PRIMARY KEY NOT NULL UNIQUE,
  name        VARCHAR(255),
  course_type VARCHAR(255)
);
CREATE TABLE online_app_system.tests
(
  id               VARCHAR(40) PRIMARY KEY NOT NULL,
  location_id      VARCHAR(40) REFERENCES online_app_system.location_types (id),
  name             VARCHAR(255),
  form_url         VARCHAR(255),
  max_points       INT,
  threshold        INT,
  enabled          BOOLEAN,
  order_in_bundle  INT,
  estimated_time   INT,
  motivation_video BOOLEAN,
  form_as_json     TEXT,
  description      TEXT
);

CREATE TABLE online_app_system.test_results
(
  id              VARCHAR(40) PRIMARY KEY NOT NULL,
  application_id  VARCHAR(40) REFERENCES online_app_system.applications (id),
  test_id         VARCHAR(40) REFERENCES online_app_system.tests (id),
  started         TIMESTAMP,
  finished        TIMESTAMP,
  points          INT,
  percent         DOUBLE PRECISION,
  passed          BOOLEAN,
  saved_answers   TEXT,
  comment         VARCHAR(255)
);


CREATE TABLE online_app_system.courses
(
  id          INT PRIMARY KEY NOT NULL,
  location_id VARCHAR(40) REFERENCES online_app_system.location_types (id),
  name        VARCHAR(255),
  open        BOOLEAN,
  start_date  TIMESTAMP,
  filled      BOOLEAN,
  enabled     BOOLEAN
);

CREATE TABLE online_app_system.test_answers (
  id             VARCHAR(40) PRIMARY KEY DEFAULT random() NOT NULL,
  question_id    VARCHAR(40) NOT NULL,
  correct_answer VARCHAR(255)
);

CREATE TABLE online_app_system.authentication (
  id            VARCHAR(255) PRIMARY KEY NOT NULL,
  token         VARCHAR(255),
  refresh_token VARCHAR(255),
  expires       TIMESTAMP,
  auth_provider VARCHAR(50)
);


CREATE TABLE online_app_system.personal_data (
  user_id          VARCHAR(255) PRIMARY KEY NOT NULL,
  name             VARCHAR(255),
  maiden_name      VARCHAR(255),
  address          VARCHAR(255),
  gmail_account    VARCHAR(255),
  mothers_name     VARCHAR(255),
  place_of_birth   VARCHAR(255),
  birth_date       TIMESTAMP,
  id_document_type VARCHAR(255),
  id_number        VARCHAR(255)
);

GRANT ALL PRIVILEGES ON SCHEMA online_app_system TO applications;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA online_app_system TO applications;
