CREATE TABLE users_patients(
id NUMBER NOT NULL,
user_id NUMBER NOT NULL,
patient_id NUMBER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES users (user_id),
FOREIGN KEY (patient_id) REFERENCES patients (patient_id)
)