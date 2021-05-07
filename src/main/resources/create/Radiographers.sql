CREATE TABLE radiographers(
doctor_id NUMBER NOT NULL,
salary_kf NUMBER NOT NULL,
vacation_days NUMBER NOT NULL,
PRIMARY KEY (doctor_id),
FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
)