CREATE TABLE patients(
patient_id NUMBER NOT NULL,
surname VARCHAR(80) NOT NULL,
name VARCHAR(50) NOT NULL,
patronymic VARCHAR(60),
birthday DATE,
phone_number VARCHAR(11),
PRIMARY KEY (patient_id)
)