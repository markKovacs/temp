
TRUNCATE TABLE online_app_system.location_types;

INSERT INTO online_app_system.location_types (id, name, course_type) VALUES ('BUD', 'Budapest, HU', 'GENERAL');
INSERT INTO online_app_system.location_types (id, name, course_type) VALUES ('KRK', 'Kraków, PL', 'GENERAL');
INSERT INTO online_app_system.location_types (id, name, course_type) VALUES ('MSC', 'Miskolc, HU', 'GENERAL');
INSERT INTO online_app_system.location_types (id, name, course_type) VALUES ('WRS', 'Warsaw, PL', 'GENERAL');

TRUNCATE TABLE online_app_system.tests;

INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('msc-2017-prereq', 'MSC', 'Prerequisites', null, 9, 9, true, 0, 3, '{"id":"msc-2017-prereq","surveyContent":"<h1>Let''s get down to business!</h1>Codecool is a private programming school which creates and implements a completely different kind of education experience and career planning. We believe that coding can change lives for the better, and the choice for this career should be open to everyone.<br/>To introduce you the‘ World of Coding’, we use practice oriented, teamwork based methods and a personalized mentoring approach.<br/> We ask you the following questions because we want to make sure that you understand what it means to be a Codecooler.You can find the relevant FAQ here: < a href = ''http://codecool.hu/gyik/'' > http: //codecool.hu/gyik/</a>","questions":[{"id":"msc-2017-prereq_0","questionContent":"<p>Do you understand that we offer not only an effective programming course but also a job opportunity?</p>","type":"radio","options":[{"id":"msc-2017-prereq_0_0","optionContent":"Yes"},{"id":"msc-2017-prereq_0_1","optionContent":"No"}]},{"id":"msc-2017-prereq_1","questionContent":"<p>Do you have a High School Degree?</p>","type":"radio","options":[{"id":"msc-2017-prereq_1_0","optionContent":"Yes"},{"id":"msc-2017-prereq_1_1","optionContent":"No"}]},{"id":"msc-2017-prereq_2","questionContent":"<p>Will you be ready to start the first semester in Miskolc from the third quarter of 2017?</p>","type":"radio","options":[{"id":"msc-2017-prereq_2_0","optionContent":"Yes"},{"id":"msc-2017-prereq_2_1","optionContent":"No"}]},{"id":"msc-2017-prereq_3","questionContent":"<p>Will you be able to spend approx. 8 hours each workday with working on projects and intensively improving your skills?</p>","type":"radio","options":[{"id":"msc-2017-prereq_3_0","optionContent":"Yes"},{"id":"msc-2017-prereq_3_1","optionContent":"No"}]},{"id":"msc-2017-prereq_4","questionContent":"<p>Do you understand that you have to spend your time together with other teammates every second week between 9-15 h in the Codecool Office?</p>","type":"radio","options":[{"id":"msc-2017-prereq_4_0","optionContent":"Yes"},{"id":"msc-2017-prereq_4_1","optionContent":"No"}]},{"id":"msc-2017-prereq_5","questionContent":"<p>Do you understand that you don''t need to pay in advance for the education, however you have to cover your living costs during your studies?</p>","type":"radio","options":[{"id":"msc-2017-prereq_5_0","optionContent":"Yes"},{"id":"msc-2017-prereq_5_1","optionContent":"No"}]},{"id":"msc-2017-prereq_6","questionContent":"<p>Do you understand that in Codecool you won''t receive a student card or student status?</p>","type":"radio","options":[{"id":"msc-2017-prereq_6_0","optionContent":"Yes"},{"id":"msc-2017-prereq_6_1","optionContent":"No"}]},{"id":"msc-2017-prereq_7","questionContent":"<p>Do you understand that Codecool won''t give you an official, accredited school certificate?</p>","type":"radio","options":[{"id":"msc-2017-prereq_7_0","optionContent":"Yes"},{"id":"msc-2017-prereq_7_1","optionContent":"No"}]},{"id":"msc-2017-prereq_8","questionContent":"<p>Do you understand that you have to finish this application process in a week?</p>","type":"radio","options":[{"id":"msc-2017-prereq_8_0","optionContent":"Yes"},{"id":"msc-2017-prereq_8_1","optionContent":"No"}]}]}', null, 'Codecool is a private programming school which creates and implements a completely different kind of education experience and career planning. We believe that coding can change lives for the better, and the choice for this career should be open to everyone.
To introduce you the ‘World of Coding’, we use practice oriented, teamwork based methods and a personalized mentoring approach.
We ask you the following questions because we want to make sure that you understand what it means to be a Codecooler. You can find the relevant FAQ here: <a href="http://codecool.hu/gyik/">http://codecool.hu/gyik/</a>');
INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('bp-2017-prereq', 'BUD', 'Prerequisites', null, 9, 9, true, 0, 3, '{"id":"bp-2017-prereq","surveyContent":"<h1>Let''s get down to business!</h1>Codecool is a private programming school which creates and implements a completely different kind of education experience and career planning. We believe that coding can change lives for the better, and the choice for this career should be open to everyone.<br/>To introduce you the‘ World of Coding’, we use practice oriented, teamwork based methods and a personalized mentoring approach.<br/> We ask you the following questions because we want to make sure that you understand what it means to be a Codecooler.You can find the relevant FAQ here: < a href = ''http://codecool.hu/gyik/'' > http: //codecool.hu/gyik/</a>","questions":[{"id":"bp-2017-prereq_0","questionContent":"<p>Do you understand that we offer not only an effective programming course but also a job opportunity?</p>","type":"radio","options":[{"id":"bp-2017-prereq_0_0","optionContent":"Yes"},{"id":"bp-2017-prereq_0_1","optionContent":"No"}]},{"id":"bp-2017-prereq_1","questionContent":"<p>Do you have a High School Degree?</p>","type":"radio","options":[{"id":"bp-2017-prereq_1_0","optionContent":"Yes"},{"id":"bp-2017-prereq_1_1","optionContent":"No"}]},{"id":"bp-2017-prereq_2","questionContent":"<p>Will you be ready to start the first semester in Budapest from the third quarter of 2017?</p>","type":"radio","options":[{"id":"bp-2017-prereq_2_0","optionContent":"Yes"},{"id":"bp-2017-prereq_2_1","optionContent":"No"}]},{"id":"bp-2017-prereq_3","questionContent":"<p>Will you be able to spend approx. 8 hours each workday with working on projects and intensively improving your skills?</p>","type":"radio","options":[{"id":"bp-2017-prereq_3_0","optionContent":"Yes"},{"id":"bp-2017-prereq_3_1","optionContent":"No"}]},{"id":"bp-2017-prereq_4","questionContent":"<p>Do you understand that you have to spend your time together with other teammates every second week between 9-15 h in the Codecool Office?</p>","type":"radio","options":[{"id":"bp-2017-prereq_4_0","optionContent":"Yes"},{"id":"bp-2017-prereq_4_1","optionContent":"No"}]},{"id":"bp-2017-prereq_5","questionContent":"<p>Do you understand that you don''t need to pay in advance for the education, however you have to cover your living costs during your studies?</p>","type":"radio","options":[{"id":"bp-2017-prereq_5_0","optionContent":"Yes"},{"id":"bp-2017-prereq_5_1","optionContent":"No"}]},{"id":"bp-2017-prereq_6","questionContent":"<p>Do you understand that in Codecool you won''t receive a student card or student status?</p>","type":"radio","options":[{"id":"bp-2017-prereq_6_0","optionContent":"Yes"},{"id":"bp-2017-prereq_6_1","optionContent":"No"}]},{"id":"bp-2017-prereq_7","questionContent":"<p>Do you understand that Codecool won''t give you an official, accredited school certificate?</p>","type":"radio","options":[{"id":"bp-2017-prereq_7_0","optionContent":"Yes"},{"id":"bp-2017-prereq_7_1","optionContent":"No"}]},{"id":"bp-2017-prereq_8","questionContent":"<p>Do you understand that you have to finish this application process in a week?</p>","type":"radio","options":[{"id":"bp-2017-prereq_8_0","optionContent":"Yes"},{"id":"bp-2017-prereq_8_1","optionContent":"No"}]}]}', null, 'Codecool is a private programming school which creates and implements a completely different kind of education experience and career planning. We believe that coding can change lives for the better, and the choice for this career should be open to everyone.
To introduce you the ‘World of Coding’, we use practice oriented, teamwork based methods and a personalized mentoring approach.
We ask you the following questions because we want to make sure that you understand what it means to be a Codecooler. You can find the relevant FAQ here: <a href="http://codecool.hu/gyik/">http://codecool.hu/gyik/</a>');

INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('bp-2017-english', 'BUD', 'English', null, 3, 2, true, 1, 30, '{"id":"bp-2017-english","surveyContent":"Dear Applicant,<br/>Congratulations! You''ve successfully started your application to Codecool!<br/>Next, please test your English level.<br/><br/>Why is it important? <br/>Because most of the learning materials are in English and it''s essential to understand the texts.<br/><br/>You can use a dictionary or online resources, but you shouldn''t ask help from friends or family members. You have to do it on your own, based on your current knowledge.<br/>Why?<br/>Because your friends and family members won''t be there with you in Codecool, and you have to be able to learn by yourself.","questions":[{"id":"bp-2017-english_0","questionContent":"Do you have an official language degree?","type":"radio","graded":false,"options":[{"id":"bp-2017-english_0_0","optionContent":"No, I don''t have."},{"id":"bp-2017-english_0_1","optionContent":"Yes, I have a ''Beginner level'' degree"},{"id":"bp-2017-english_0_2","optionContent":"Yes, I have an ''Intermediate level'' degree"},{"id":"bp-2017-english_0_3","optionContent":"Yes, I have an ''Advanced level'' degree"}]},{"id":"bp-2017-english_1","questionContent":"What do you think, what is your level of understanding English?","type":"radio","graded":false,"options":[{"id":"bp-2017-english_1_0","optionContent":"Beginner"},{"id":"bp-2017-english_1_1","optionContent":"Intermediate"},{"id":"bp-2017-english_1_2","optionContent":"Advanced"}]},{"id":"bp-2017-english_2","questionContent":"Do you understand that it''s important to test your English skills and you will complete this test by your own?","type":"radio","options":[{"id":"bp-2017-english_2_0","optionContent":"Yes"},{"id":"bp-2017-english_2_1","optionContent":"No"}]},{"id":"bp-2017-english_3","questionContent":"<h1>Please read the following text carefully!</h1><h2>Story</h2><br/>Peter learns programming at Codecool. He is eager to improve his ability to learn; therefore he became very excited when he found the following article on mindsetworks.com.<br/><h2>Is Gamification Important in 2017? Can gamification (and gamification platforms) really drive business and engagement?</h2>You’ve decided to use gamification in 2017, and you want to choose the best platform. Gamification is actually a high-risk choice, because if you use it, you can’t afford to get mediocre results. You want to shine as the one who had the vision, creativity, and courage to use it and transform your business and engagement. Otherwise, it isn’t worth the effort. <br />Gamification can look like this in your business:<ul><li>- For consumers, you want to get more engagement, brand recognition etc.</li></ul><ul><li>- For employee performance, you want gamificaiton to deliver real return-on-investment, not just some badges and points.</li></ul><br/>So, if gamification is strategic to you — regardless of whether you want to gamify learning, employee performance or to get teens to buy more of your balsamic onion ice-cream — you probably want to know what the best gamification platforms are for 2017. That’s a tough one, because, sometimes, you should not be looking for a gamification platform in the first place.<br/>Now please answer the following questions based on the text above!<br/>Please choose all the TRUE statements from the following options:","type":"checkbox","options":[{"id":"bp-2017-english_3_0","optionContent":"Gamification is worth the effort only if the results are excellent."},{"id":"bp-2017-english_3_1","optionContent":"You should always start the process by looking for the right gamification platform."},{"id":"bp-2017-english_3_2","optionContent":"Gamification is risky because it is very expensive."},{"id":"bp-2017-english_3_0","optionContent":"Employee performance can be gamified."}]},{"id":"bp-2017-english_4","questionContent":" <h2 > Before we begin,here are four  prickly truths about gamification: < /h2>Gamification doesn’t always require a platform I often get asked whether a gamification platform can be added to a mobile app, consumer website, etc. There’s a reason for these questions: people hear that gamification can drive engagement. They’ve heard of Pokémon Go and they want the same level of fun and engagement for what they’ve been working on. <br/ > The only problem is that  points, badges and leaderboards don’ t make engagement  and aren’ t a guarantee that users will be enamored with your product.Need convincing ? < br / > Please choose all the TRUE statements from the following options : ","type":"checkbox","options":[{"id":"bp-2017-english_4_0","optionContent":"People often use Pokémon Go as an example when defining their goals with gamification."},{"id":"bp-2017-english_4_1","optionContent":"Consumers would like a game added to the app they are using."},{"id":"bp-2017-english_4_2","optionContent":"Points guarantee customer satisfaction."}]},{"id":"bp-2017-english_5","questionContent":"Think about what would happen if you got 10 points for reading this article.Would you care ? I don’ t think so.Would you be annoyed ? Maybe, but this is what many“ gamification” platforms do .My advice ? If you want to gamify your app / software / business, do something smart— use gamification as part of the design of the app / software / business, instead of just slapping it on like lipstick— it won’ t work.Gamification works when it is nicely integrated into the app and when it is gamefully designed.There are stories about how it works (and then didn’ t work) for Foursquare, and how it worked well for Karma points and LinkedIn. < br / > Please choose all the TRUE statements from the following options : ","type":"checkbox","options":[{"id":"bp-2017-english_5_0","optionContent":"Gamification platforms are annoying."},{"id":"bp-2017-english_5_1","optionContent":"LinkedIn has used gamification successfully."},{"id":"bp-2017-english_5_2","optionContent":"It is important that the game has a story."}]}]}', null, 'Small desc. test for the English test comes here.');
INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('msc-2017-english', 'MSC', 'English', null, 3, 2, true, 1, 30, '{"id":"msc-2017-english","surveyContent":"Dear Applicant,<br/>Congratulations! You''ve successfully started your application to Codecool!<br/>Next, please test your English level.<br/><br/>Why is it important? <br/>Because most of the learning materials are in English and it''s essential to understand the texts.<br/><br/>You can use a dictionary or online resources, but you shouldn''t ask help from friends or family members. You have to do it on your own, based on your current knowledge.<br/>Why?<br/>Because your friends and family members won''t be there with you in Codecool, and you have to be able to learn by yourself.","questions":[{"id":"msc-2017-english_0","questionContent":"Do you have an official language degree?","type":"radio","graded":false,"options":[{"id":"msc-2017-english_0_0","optionContent":"No, I don''t have."},{"id":"msc-2017-english_0_1","optionContent":"Yes, I have a ''Beginner level'' degree"},{"id":"msc-2017-english_0_2","optionContent":"Yes, I have an ''Intermediate level'' degree"},{"id":"msc-2017-english_0_3","optionContent":"Yes, I have an ''Advanced level'' degree"}]},{"id":"msc-2017-english_1","questionContent":"What do you think, what is your level of understanding English?","type":"radio","graded":false,"options":[{"id":"msc-2017-english_1_0","optionContent":"Beginner"},{"id":"msc-2017-english_1_1","optionContent":"Intermediate"},{"id":"msc-2017-english_1_2","optionContent":"Advanced"}]},{"id":"msc-2017-english_2","questionContent":"Do you understand that it''s important to test your English skills and you will complete this test by your own?","type":"radio","options":[{"id":"msc-2017-english_2_0","optionContent":"Yes"},{"id":"msc-2017-english_2_1","optionContent":"No"}]},{"id":"msc-2017-english_3","questionContent":"<h1>Please read the following text carefully!</h1><h2>Story</h2><br/>Peter learns programming at Codecool. He is eager to improve his ability to learn; therefore he became very excited when he found the following article on mindsetworks.com.<br/><h2>Is Gamification Important in 2017? Can gamification (and gamification platforms) really drive business and engagement?</h2>You’ve decided to use gamification in 2017, and you want to choose the best platform. Gamification is actually a high-risk choice, because if you use it, you can’t afford to get mediocre results. You want to shine as the one who had the vision, creativity, and courage to use it and transform your business and engagement. Otherwise, it isn’t worth the effort. <br />Gamification can look like this in your business:<ul><li>- For consumers, you want to get more engagement, brand recognition etc.</li></ul><ul><li>- For employee performance, you want gamificaiton to deliver real return-on-investment, not just some badges and points.</li></ul><br/>So, if gamification is strategic to you — regardless of whether you want to gamify learning, employee performance or to get teens to buy more of your balsamic onion ice-cream — you probably want to know what the best gamification platforms are for 2017. That’s a tough one, because, sometimes, you should not be looking for a gamification platform in the first place.<br/>Now please answer the following questions based on the text above!<br/>Please choose all the TRUE statements from the following options:","type":"checkbox","options":[{"id":"msc-2017-english_3_0","optionContent":"Gamification is worth the effort only if the results are excellent."},{"id":"msc-2017-english_3_1","optionContent":"You should always start the process by looking for the right gamification platform."},{"id":"msc-2017-english_3_2","optionContent":"Gamification is risky because it is very expensive."},{"id":"msc-2017-english_3_0","optionContent":"Employee performance can be gamified."}]},{"id":"msc-2017-english_4","questionContent":" <h2 > Before we begin,here are four  prickly truths about gamification: < /h2>Gamification doesn’t always require a platform I often get asked whether a gamification platform can be added to a mobile app, consumer website, etc. There’s a reason for these questions: people hear that gamification can drive engagement. They’ve heard of Pokémon Go and they want the same level of fun and engagement for what they’ve been working on. <br/ > The only problem is that  points, badges and leaderboards don’ t make engagement  and aren’ t a guarantee that users will be enamored with your product.Need convincing ? < br / > Please choose all the TRUE statements from the following options : ","type":"checkbox","options":[{"id":"msc-2017-english_4_0","optionContent":"People often use Pokémon Go as an example when defining their goals with gamification."},{"id":"msc-2017-english_4_1","optionContent":"Consumers would like a game added to the app they are using."},{"id":"msc-2017-english_4_2","optionContent":"Points guarantee customer satisfaction."}]},{"id":"msc-2017-english_5","questionContent":"Think about what would happen if you got 10 points for reading this article.Would you care ? I don’ t think so.Would you be annoyed ? Maybe, but this is what many“ gamification” platforms do .My advice ? If you want to gamify your app / software / business, do something smart— use gamification as part of the design of the app / software / business, instead of just slapping it on like lipstick— it won’ t work.Gamification works when it is nicely integrated into the app and when it is gamefully designed.There are stories about how it works (and then didn’ t work) for Foursquare, and how it worked well for Karma points and LinkedIn. < br / > Please choose all the TRUE statements from the following options : ","type":"checkbox","options":[{"id":"msc-2017-english_5_0","optionContent":"Gamification platforms are annoying."},{"id":"msc-2017-english_5_1","optionContent":"LinkedIn has used gamification successfully."},{"id":"msc-2017-english_5_2","optionContent":"It is important that the game has a story."}]}]}', null, 'Small desc. test for the English test comes here.');
INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('msc-2017-logic', 'MSC', 'Logical test', null, 3, 2, true, 2, 45, '{"id":"msc-2017-logic","surveyContent":"<h1>Codecool - Test your logical thinking!</h1>Dear Applicant,<br/>Congratulations! You''re made great progress with your application to Codecool!Next, please test your logical thinking and problem solving skills.<br/>Why is it important?<br/>Even if you don''t know (yet) how to write programs, you can assess your relevant competencies like logical reasoning, numerical problem solving, pattern recognition, and the ability to follow complex procedures.<br/>The more you practice these, the more you will understand the world of programmers.<br/>In this test, you will find 2 example questions and then 20 real questions (on 4 pages).<br/>Please use SCRAP PAPER and a CALCULATOR for working out answers.","questions":[{"id":"msc-2017-logic_0","questionContent":"Do you understand that it''s important to test your skills and you will complete this test by your own?","type":"radio","options":[{"id":"msc-2017-logic_0_0","optionContent":"Yes"},{"id":"msc-2017-logic_0_1","optionContent":"No"}]},{"id":"msc-2017-logic_1","questionContent":"<img src=''http://imgur.com/LnQwffE'' /> Which figure fits to the place of the question mark logically?","type":"radio","options":[{"id":"msc-2017-logic_1_0","optionContent":"top left"},{"id":"msc-2017-logic_1_1","optionContent":"top right"},{"id":"msc-2017-logic_1_2","optionContent":"bottom left"},{"id":"msc-2017-logic_1_3","optionContent":"bottom right"}]},{"id":"msc-2017-logic_1","questionContent":"<img src=''http://imgur.com/ZO2aNdd'' /> What is B1 + C2?","type":"radio","options":[{"id":"msc-2017-logic_1_0","optionContent":"10"},{"id":"msc-2017-logic_1_1","optionContent":"11"},{"id":"msc-2017-logic_1_2","optionContent":"12"},{"id":"msc-2017-logic_1_3","optionContent":"13"},{"id":"msc-2017-logic_1_4","optionContent":"14"},{"id":"msc-2017-logic_1_5","optionContent":"None of these"}]},{"id":"msc-2017-logic_2","questionContent":"Please take a look at the following figure. Which figure fits to the place of the question mark logically? <img src=''http://imgur.com/mCbfWAx'' />","type":"radio","options":[{"id":"msc-2017-logic_2_0","optionContent":"http://imgur.com/ucuVTtQ"},{"id":"msc-2017-logic_2_1","optionContent":"http://imgur.com/iedAYyN"},{"id":"msc-2017-logic_2_2","optionContent":"http://imgur.com/MvxxlF7"},{"id":"msc-2017-logic_2_3","optionContent":"http://imgur.com/KYpozqd"},{"id":"msc-2017-logic_2_4","optionContent":"http://imgur.com/1Ps0npG"}]}]}', null, 'Test your Logical thinking!');
INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('bp-2017-logic', 'BUD', 'Logical test', null, 3, 2, true, 2, 45, '{"id":"bp-2017-logic","surveyContent":"<h1>Codecool - Test your logical thinking!</h1>Dear Applicant,<br/>Congratulations! You''re made great progress with your application to Codecool!Next, please test your logical thinking and problem solving skills.<br/>Why is it important?<br/>Even if you don''t know (yet) how to write programs, you can assess your relevant competencies like logical reasoning, numerical problem solving, pattern recognition, and the ability to follow complex procedures.<br/>The more you practice these, the more you will understand the world of programmers.<br/>In this test, you will find 2 example questions and then 20 real questions (on 4 pages).<br/>Please use SCRAP PAPER and a CALCULATOR for working out answers.","questions":[{"id":"bp-2017-logic_0","questionContent":"Do you understand that it''s important to test your skills and you will complete this test by your own?","type":"radio","options":[{"id":"bp-2017-logic_0_0","optionContent":"Yes"},{"id":"bp-2017-logic_0_1","optionContent":"No"}]},{"id":"bp-2017-logic_1","questionContent":"<img src=''http://imgur.com/LnQwffE'' /> Which figure fits to the place of the question mark logically?","type":"radio","options":[{"id":"bp-2017-logic_1_0","optionContent":"top left"},{"id":"bp-2017-logic_1_1","optionContent":"top right"},{"id":"bp-2017-logic_1_2","optionContent":"bottom left"},{"id":"bp-2017-logic_1_3","optionContent":"bottom right"}]},{"id":"bp-2017-logic_2","questionContent":"<img src=''http://imgur.com/ZO2aNdd'' /> What is B1 + C2?","type":"radio","options":[{"id":"bp-2017-logic_2_0","optionContent":"10"},{"id":"bp-2017-logic_2_1","optionContent":"11"},{"id":"bp-2017-logic_2_2","optionContent":"12"},{"id":"bp-2017-logic_2_3","optionContent":"13"},{"id":"bp-2017-logic_2_4","optionContent":"14"},{"id":"bp-2017-logic_2_5","optionContent":"None of these"}]},{"id":"bp-2017-logic_3","questionContent":"Please take a look at the following figure. Which figure fits to the place of the question mark logically? <img src=''http://imgur.com/mCbfWAx'' />","type":"radio","options":[{"id":"bp-2017-logic_3_0","optionContent":"http://imgur.com/ucuVTtQ"},{"id":"bp-2017-logic_3_1","optionContent":"http://imgur.com/iedAYyN"},{"id":"bp-2017-logic_3_2","optionContent":"http://imgur.com/MvxxlF7"},{"id":"bp-2017-logic_3_3","optionContent":"http://imgur.com/KYpozqd"},{"id":"bp-2017-logic_3_4","optionContent":"http://imgur.com/1Ps0npG"}]}]}', null, 'Test your Logical thinking!');
INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('bp-2017-motiv', 'BUD', 'Motivation video', null, 1, 1, true, 3, 120, '{"id":"bp-2017-motiv","surveyContent":"Dear Applicant, you should enter your motivation video url here.","questions":[{"id":"bp-2017-motiv_0","questionContent":"Enter Your Motivation Video Here","type":"freetext","graded":false,"options":[]}]}', true, 'This IS going TO be the motivation short description.');
INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('msc-2017-motiv', 'MSC', 'Motivation video', null, 1, 1, true, 3, 120, '{"id":"msc-2017-motiv","surveyContent":"Dear Applicant, you should enter your motivation video url here.","questions":[{"id":"msc-2017-motiv_0","questionContent":"Enter Your Motivation Video Here","type":"freetext","graded":false,"options":[]}]}', true, 'This IS going TO be the motivation short description.');

INSERT INTO online_app_system.tests (id, location_id, name, form_url, max_points, threshold, enabled, order_in_bundle, estimated_time, form_as_json, motivation_video, description) VALUES ('bp-2017-motiv', 'BUD', 'Motivation', null, 3, 2, true, 3, 120, '{"id":"bp-2017-motiv", "surveyContent":"<h1>Dear Applicant!</h1><p>Now it''s time to tell us about your motivations. Why do you want to be a Codecooler? Please write a few sentences about you! You can even submit a YouTube video URL here.</p>", "questions":[{"id":"bp-2017-motiv_0", "questionContent":"<h1>Please tell us a bit more about what motivates you!</h1>", "type":"freetext", "graded":false, "options":[]}]}', true, 'This is going to be the motivation short description.');

TRUNCATE TABLE online_app_system.test_answers;

INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_0', 'msc-2017-prereq_0_0', '0.265246657188981771');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_1', 'msc-2017-prereq_1_0', '0.0922373645007610321');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_2', 'msc-2017-prereq_2_0', '0.795557135716080666');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_3', 'msc-2017-prereq_3_0', '0.0067258886992931366');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_4', 'msc-2017-prereq_4_0', '0.860971872694790363');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_5', 'msc-2017-prereq_5_0', '0.229172783903777599');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_6', 'msc-2017-prereq_6_0', '0.575127967167645693');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_7', 'msc-2017-prereq_7_0', '0.755940886214375496');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-prereq_8', 'msc-2017-prereq_8_0', '0.0547590712085366249');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_0', 'bp-2017-prereq_0_0', '0.0427989964373409748');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_1', 'bp-2017-prereq_1_0', '0.716038286685943604');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_2', 'bp-2017-prereq_2_0', '0.785441668704152107');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_3', 'bp-2017-prereq_3_0', '0.705653220880776644');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_4', 'bp-2017-prereq_4_0', '0.603165051434189081');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_5', 'bp-2017-prereq_5_0', '0.981414550915360451');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_6', 'bp-2017-prereq_6_0', '0.442622937262058258');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_7', 'bp-2017-prereq_7_0', '0.00972380070015788078');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-prereq_8', 'bp-2017-prereq_8_0', '0.136266613844782114');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-english_3', 'msc-2017-english_3_0', '0.00585280032828450203');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-english_3', 'msc-2017-english_3_1', '0.488722883630543947');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-english_4', 'msc-2017-english_3_0', '0.193321081344038248');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-english_4', 'msc-2017-english_4_1', '0.526061153970658779');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-english_5', 'msc-2017-english_3_0', '0.428316870238631964');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-english_5', 'msc-2017-english_5_1', '0.446133037563413382');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-english_3', 'bp-2017-english_3_0', '0.493731356225907803');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-english_3', 'bp-2017-english_3_1', '0.0158385518006980419');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-english_4', 'bp-2017-english_3_0', '0.800357525236904621');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-english_4', 'bp-2017-english_4_1', '0.0379430525936186314');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-english_5', 'bp-2017-english_3_0', '0.686311982572078705');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-english_5', 'bp-2017-english_5_1', '0.582631674129515886');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-english_2', 'msc-2017-english_2_0', '0.41604705061763525');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-english_2', 'bp-2017-english_2_0', '0.134320397395640612');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-logic_0', 'bp-2017-logic_0_0', '0.882501068059355021');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-logic_1', 'bp-2017-logic_1_0', '0.9853177759796381');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-logic_2', 'bp-2017-logic_2_0', '0.381639121100306511');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('bp-2017-logic_3', 'bp-2017-logic_3_0', '0.815484256017953157');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-logic_0', 'msc-2017-logic_0_0', '0.282278594560921192');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-logic_1', 'msc-2017-logic_1_0', '0.116865295916795731');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-logic_2', 'msc-2017-logic_2_0', '0.910760650411248207');
INSERT INTO online_app_system.test_answers (question_id, correct_answer, id) VALUES ('msc-2017-logic_3', 'msc-2017-logic_3_0', '0.115833332296460867');