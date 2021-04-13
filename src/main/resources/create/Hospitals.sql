CREATE TABLE hospitals(
hospital_id NUMBER NOT NULL,
name VARCHAR(300) NOT NULL,
address VARCHAR(400),
main_doctor_id NUMBER NOT NULL,
phone_number VARCHAR(11),
PRIMARY KEY (hospital_id),
FOREIGN KEY (main_doctor_id) REFERENCES doctors (doctor_id)
)