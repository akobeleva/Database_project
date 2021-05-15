CREATE TABLE polyclinic_card(
card_id NUMBER NOT NULL,
polyclinic_id NUMBER NOT NULL,
patient_id NUMBER NOT NULL,
doctor_id NUMBER NOT NULL,
visit_date DATE NOT NULL,
PRIMARY KEY (card_id),
FOREIGN KEY (patient_id) REFERENCES patients (patient_id),
FOREIGN KEY (polyclinic_id) REFERENCES polyclinics (polyclinic_id),
FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
)