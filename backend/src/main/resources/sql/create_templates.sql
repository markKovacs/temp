DROP TABLE IF EXISTS online_app_system.email_templates;

CREATE TABLE online_app_system.email_templates
(
  id               VARCHAR(40) PRIMARY KEY NOT NULL,
  location_id      VARCHAR(40) REFERENCES online_app_system.location_types (id),
  name             VARCHAR(255),
  template         TEXT,
  model            TEXT,
  master           BOOLEAN
);

GRANT ALL PRIVILEGES ON SCHEMA online_app_system TO applications;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA online_app_system TO applications;

INSERT INTO online_app_system.email_templates (id, location_id, name, master) VALUES ('0.257776657188981771', 'BUD', 'Budapest master template', true);
INSERT INTO online_app_system.email_templates (id, location_id, name, master) VALUES ('0.258886657188981771', 'BUD', 'Welcome email template', false);
INSERT INTO online_app_system.email_templates (id, location_id, name, master) VALUES ('0.259996657188981771', 'BUD', 'Success email template', false);