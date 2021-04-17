CREATE TABLE doctors(
doctor_id NUMBER NOT NULL,
surname VARCHAR(80) NOT NULL,
name VARCHAR(50) NOT NULL,
patronymic VARCHAR(60),
speciality_id NUMBER NOT NULL,
dms NUMBER(1) DEFAULT 0,
cms NUMBER(1) DEFAULT 0,
professor NUMBER(1) DEFAULT 0,
docent NUMBER(1) DEFAULT 0,
experience NUMBER NOT NULL,
PRIMARY KEY (doctor_id),
FOREIGN KEY (speciality_id) REFERENCES specialities (speciality_id)
)