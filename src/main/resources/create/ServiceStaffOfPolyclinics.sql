CREATE TABLE ss_of_polyclinics(
id NUMBER NOT NULL,
polyclinic_id NUMBER NOT NULL,
ss_id NUMBER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (polyclinic_id) REFERENCES polyclinics (polyclinic_id),
FOREIGN KEY (ss_id) REFERENCES service_staff (ss_id)
)