create table online_app_system.screening_step
(
    id varchar(40) not null
        constraint screening_step_pkey
            primary key,
    name varchar(255) not null,
    location_id varchar(40) not null,
    enabled boolean default true
)
;


[9:11] 
create table online_app_system.screening_step_criteria
(
    id varchar(40) not null
        constraint screening_step_criteria_pkey
            primary key,
    name varchar(255) not null,
    screening_step_id varchar(40) not null,
    enabled boolean default true
)
;