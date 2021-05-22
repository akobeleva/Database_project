CREATE TABLE users_hospitals(
id NUMBER NOT NULL,
user_id NUMBER NOT NULL,
hospital_id NUMBER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES users (user_id),
FOREIGN KEY (hospital_id) REFERENCES hospitals (hospital_id)
)