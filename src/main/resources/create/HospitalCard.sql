CREATE TABLE hospital_card(
card_id NUMBER NOT NULL,
patient_id NUMBER NOT NULL,
department_id NUMBER NOT NULL,
start_date DATE NOT NULL,
end_date DATE NOT NULL,
health_status VARCHAR(80) NOT NULL,
temp FLOAT NOT NULL,
PRIMARY KEY (card_id),
FOREIGN KEY (patient_id) REFERENCES patients (patient_id),
FOREIGN KEY (department_id) REFERENCES departments (department_id)
)