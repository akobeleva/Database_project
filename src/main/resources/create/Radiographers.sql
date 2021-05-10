CREATE TABLE radiographers(
doctor_id NUMBER NOT NULL,
salary_kf NUMBER,
vacation_days NUMBER,
PRIMARY KEY (doctor_id),
FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
)