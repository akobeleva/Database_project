CREATE TABLE ss_of_hospitals(
id NUMBER NOT NULL,
hospital_id NUMBER NOT NULL,
ss_id NUMBER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (hospital_id) REFERENCES hospitals (hospital_id),
FOREIGN KEY (ss_id) REFERENCES service_staff (ss_id)
)