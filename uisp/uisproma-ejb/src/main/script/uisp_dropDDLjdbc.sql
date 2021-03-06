ALTER TABLE ATHLETE DROP FOREIGN KEY FK_ATHLETE_CLUB_ID;
ALTER TABLE ATTACHE DROP FOREIGN KEY FK_ATTACHE_USER_USERNAME;
ALTER TABLE ATTACHE DROP FOREIGN KEY FK_ATTACHE_CLUB_ID;
ALTER TABLE CHAMPIONSHIP DROP FOREIGN KEY FK_CHAMPIONSHIP_SPORT_ID;
ALTER TABLE CHAMPIONSHIP DROP FOREIGN KEY FK_CHAMPIONSHIP_CURATOR_ID;
ALTER TABLE COMPETITION DROP FOREIGN KEY FK_COMPETITION_SPORT_ID;
ALTER TABLE COMPETITION DROP FOREIGN KEY FK_COMPETITION_CURATOR_ID;
ALTER TABLE CURATOR DROP FOREIGN KEY FK_CURATOR_USER_USERNAME;
ALTER TABLE ROLE DROP FOREIGN KEY FK_ROLE_USER_USERNAME;
ALTER TABLE PAYMENT DROP FOREIGN KEY FK_PAYMENT_ATTACHE_ID;
ALTER TABLE PAYMENT DROP FOREIGN KEY FK_PAYMENT_CURATOR_ID;
ALTER TABLE CHAMPIONSHIPATTENDANCE DROP FOREIGN KEY FK_CHAMPIONSHIPATTENDANCE_ATTACHE_ID;
ALTER TABLE CHAMPIONSHIPATTENDANCE DROP FOREIGN KEY FK_CHAMPIONSHIPATTENDANCE_CHAMPIONSHIP_ID;
ALTER TABLE CHAMPIONSHIPATTENDANCE DROP FOREIGN KEY FK_CHAMPIONSHIPATTENDANCE_ATHLETE_ID;
ALTER TABLE COMPETITIONATTENDANCE DROP FOREIGN KEY FK_COMPETITIONATTENDANCE_ATHLETE_ID;
ALTER TABLE COMPETITIONATTENDANCE DROP FOREIGN KEY FK_COMPETITIONATTENDANCE_COMPETITION_ID;
ALTER TABLE COMPETITIONATTENDANCE DROP FOREIGN KEY FK_COMPETITIONATTENDANCE_SOLUTION_ID;
ALTER TABLE COMPETITIONATTENDANCE DROP FOREIGN KEY FK_COMPETITIONATTENDANCE_ATTACHE_ID;
ALTER TABLE CHAMPIONSHIPFEE DROP FOREIGN KEY FK_CHAMPIONSHIPFEE_ATTENDANCE_ID;
ALTER TABLE CHAMPIONSHIPFEE DROP FOREIGN KEY FK_CHAMPIONSHIPFEE_STEP_ID;
ALTER TABLE CHAMPIONSHIPFEE DROP FOREIGN KEY FK_CHAMPIONSHIPFEE_PAYMENT_ID;
ALTER TABLE COMPETITIONFEE DROP FOREIGN KEY FK_COMPETITIONFEE_ATTENDANCE_ID;
ALTER TABLE COMPETITIONFEE DROP FOREIGN KEY FK_COMPETITIONFEE_PAYMENT_ID;
ALTER TABLE CHAMPIONSHIP_STEP DROP FOREIGN KEY FK_CHAMPIONSHIP_STEP_steps_ID;
ALTER TABLE CHAMPIONSHIP_STEP DROP FOREIGN KEY FK_CHAMPIONSHIP_STEP_Championship_ID;
ALTER TABLE COMPETITION_SOLUTION DROP FOREIGN KEY FK_COMPETITION_SOLUTION_Competition_ID;
ALTER TABLE COMPETITION_SOLUTION DROP FOREIGN KEY FK_COMPETITION_SOLUTION_solutions_ID;
ALTER TABLE EVENT DROP FOREIGN KEY FK_EVENT_CURATOR_ID;
ALTER TABLE EVENT DROP FOREIGN KEY FK_EVENT_SPORT_ID;
ALTER TABLE ATTENDANCE DROP FOREIGN KEY FK_ATTENDANCE_ATHLETE_ID;
ALTER TABLE ATTENDANCE DROP FOREIGN KEY FK_ATTENDANCE_ATTACHE_ID;
ALTER TABLE FEE DROP FOREIGN KEY FK_FEE_PAYMENT_ID;
DROP TABLE USER;
DROP TABLE ATHLETE;
DROP TABLE ATTACHE;
DROP TABLE CHAMPIONSHIP;
DROP TABLE COMPETITION;
DROP TABLE CURATOR;
DROP TABLE SPORT;
DROP TABLE SPORTSCLUB;
DROP TABLE ROLE;
DROP TABLE SOLUTION;
DROP TABLE STEP;
DROP TABLE PAYMENT;
DROP TABLE CHAMPIONSHIPATTENDANCE;
DROP TABLE COMPETITIONATTENDANCE;
DROP TABLE CHAMPIONSHIPFEE;
DROP TABLE COMPETITIONFEE;
DROP TABLE CHAMPIONSHIP_STEP;
DROP TABLE COMPETITION_SOLUTION;
DROP TABLE EVENT;
DROP TABLE ATTENDANCE;
DROP TABLE FEE;
