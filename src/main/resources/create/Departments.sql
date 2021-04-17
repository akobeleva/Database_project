CREATE TABLE departments(
department_id NUMBER NOT NULL,
name VARCHAR(300) NOT NULL,
building_id NUMBER NOT NULL,
phone_number VARCHAR(11),
wards NUMBER,
beds NUMBER,
free_wards NUMBER,
free_beds NUMBER,
PRIMARY KEY (department_id),
FOREIGN KEY (building_id) REFERENCES buildings (building_id)
)