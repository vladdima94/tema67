DROP TABLE schoolsTbl;
DROP TABLE studentTBL;
DROP TABLE RESULTSTBL;
CREATE TABLE schoolsTbl
(
  schoolID INTEGER NOT NULL,
  schoolName VARCHAR (30),
  maxApply INTEGER NOT NULL,
  preferences VARCHAR(100)
);
CREATE TABLE studentTBL
(
  studentID INTEGER NOT NULL,
  studentName VARCHAR(30),
  studentPassword VARCHAR(30),
  studentType VARCHAR(10),
  preferences VARCHAR(100)
);
CREATE TABLE RESULTSTBL
(
  STUDENTNAME VARCHAR(30),
  SCHOOLNAME VARCHAR(30)
);
INSERT INTO STUDENTTBL(studentID, studentName, studentPassword, studentType, preferences) VALUES (0, 'vladdima', 'asd', 'admin', '');
commit;