CREATE TABLE surgeons(
doctor_id NUMBER NOT NULL,
numb_op NUMBER,
numb_fatal_op NUMBER,
PRIMARY KEY (doctor_id),
FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
)