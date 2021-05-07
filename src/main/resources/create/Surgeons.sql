CREATE TABLE surgeons(
doctor_id NUMBER NOT NULL,
numb_op NUMBER NOT NULL,
numb_fatal_op NUMBER NOT NULL,
PRIMARY KEY (doctor_id),
FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
)