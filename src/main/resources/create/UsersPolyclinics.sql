CREATE TABLE users_polyclinics(
id NUMBER NOT NULL,
user_id NUMBER NOT NULL,
polyclinic_id NUMBER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES users (user_id),
FOREIGN KEY (polyclinic_id) REFERENCES polyclinics (polyclinic_id)
)