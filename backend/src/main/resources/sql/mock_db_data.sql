﻿INSERT INTO online_app_system.system_user (id, admin_id, given_name, family_name, middle_name, location_id, registered_at, gender, birth_date, photo_url, phone_number, can_apply, gmail_account)
VALUES
  ('old@gmail.com',1,'janos', 'toth','','BUD','2017.06.01','male', 1983 ,'','06701234567',true,true),
  ('middle@gmail.com',2,'Anna', '','Kovacs','MSC','2017.06.11','female',1999,'','06701234567',true,true),
  ('appdet@gmail.com',6,'Applicationkktek', 'Details','Test','BUD','2017.06.11','female',1999,'','06701234567',true,true);
-- girhes.cc.2016@gmail.com
-- Girhes2016
-- InternOldApp@gmail.COM
-- Intern123



INSERT INTO online_app_system.system_user (id, registered_at, can_apply, gmail_account)
VALUES ('new@gmail.com','2017.06.21',false,true);

INSERT INTO online_app_system.applications (id, applicant_id, course_id,
                                            location_id, process_started_at,final_result,
                                            contract_signed,active)
VALUES
  ('1', 'old@gmail.com', 1, 'BUD', CURRENT_DATE, false, false,TRUE ),
  ('2', 'middle@gmail.com', 2, 'MSC', CURRENT_DATE, false, false,TRUE ),
  ('3', 'appdet@gmail.com', 1, 'BUD', CURRENT_DATE, false, false,TRUE ),
  ('4', 'appdet@gmail.com', 1, 'BUD', CURRENT_DATE, false, false,FALSE );

INSERT INTO online_app_system.sent_emails (id, application_id, initial,
                                           info, reminder, deadline_reminder, schedule_or_failed,
                                           schedule_reminder, screening_thanks)
VALUES ('1', '1', 'yesno','come come come', 'reminder1', 'deadline', 'failed', 'schendule reminder', 'screening' );

INSERT INTO online_app_system.application_screening_info (id, application_id,
                                                          screening_day, screening_group_time, screening_personal_time,
                                                          schedule_signed_back)
VALUES ('1', '1', '2018-12-31', '11:00', '12:00' , true),
  ('2', '2', '2018-12-31', '13:00', '15:00' , false),
  ('3', '3', '2018-12-31', '13:02', '15:07' , false);


INSERT INTO online_app_system.tests (id, location_id, name, form_url,
                                     max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video)
VALUES
  ('1', 'BUD', 'introduction', 'www', 100, 60, true,1,20,'{"language":"en","gender":"male"}',false),
  ('2', 'MSC', 'introduction', 'www', 100, 60, true,1,20,'{"language":"en","gender":"male"}',false),
  ('3', 'MSC', 'english', 'www', 100, 60, true,2,40,'{"language":"en","gender":"male"}',false),
  ('4', 'BUD', 'english', 'www', 100, 60, true,2,40,'{"language":"en","gender":"male"}',false),
  ('5', 'KRK', 'test', 'www', 100, 60, true,3,50,'{"language":"en","gender":"male"}',false),
  ('english', 'BUD', 'english', 'www', 100,2, true,2,2,'{"language":"en","gender":"male"}',false),
  ('6', 'BUD', 'test', 'www', 100, 60, true,3,50,'{"language":"en","gender":"male"}',false),
  ('7', 'WRS', 'motivation', 'www', 100, 60, true,4,60,'{"language":"en","gender":"male"}',true),
  ('8', 'BUD', 'motivation', 'www', 100, 60, true,4,60,'{"language":"en","gender":"male"}',true);

INSERT INTO online_app_system.courses (id, location_id, name, open, start_date, filled, enabled)
VALUES
  (1, 'BUD','GENERAL', 'yes', '2017.09.19', 'false', 'true'),
  (2, 'KRK', 'GENERAL', 'yes', '2017.09.19', 'false', 'true');

INSERT INTO online_app_system.test_results (id, application_id, test_id, started, finished, points, percent, passed, comment)
VALUES
  ('1','1', '1',CURRENT_DATE,CURRENT_DATE, 87,87, true, 'comment'),
  ('2','1', '4',CURRENT_DATE,CURRENT_DATE, 87,87, true, 'comment'),
  ('3','1', '5',CURRENT_DATE,CURRENT_DATE, 10,10, true, 'comment'),
  ('4','2', '2',CURRENT_DATE,CURRENT_DATE, 99,99, true, 'comment'),
  ('5','2', '3',CURRENT_DATE,CURRENT_DATE, 76,76, false, 'comment'),
  ('6','3', '3',CURRENT_DATE,CURRENT_DATE, 76,76, false, 'comment');

INSERT INTO online_app_system.test_results (id, application_id, test_id, started, passed, comment)
VALUES
  ('8','2', '3',CURRENT_DATE, false,'comment');

INSERT INTO online_app_system.test_answers (question_id, correct_answer)
VALUES ('english_1','b,c'),
  ('english_2','c'),
  ('english_3','a');
