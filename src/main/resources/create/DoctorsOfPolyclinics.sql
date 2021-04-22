CREATE TABLE doctors_of_polyclinics(
id NUMBER NOT NULL,
polyclinic_id NUMBER NOT NULL,
doctor_id NUMBER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (polyclinic_id) REFERENCES polyclinics (polyclinic_id),
FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
)