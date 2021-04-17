CREATE TABLE buildings(
building_id NUMBER NOT NULL,
hospital_id NUMBER NOT NULL,
building_number NUMBER NOT NULL,
phone_number VARCHAR(11),
count_departments NUMBER,
PRIMARY KEY (building_id),
FOREIGN KEY (hospital_id) REFERENCES hospitals (hospital_id)
)