CREATE TABLE doctors_of_hospitals(
id NUMBER NOT NULL,
hospital_id NUMBER NOT NULL,
doctor_id NUMBER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (hospital_id) REFERENCES hospitals (hospital_id),
FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
)