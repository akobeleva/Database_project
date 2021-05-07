CREATE TABLE service_staff(
ss_id NUMBER NOT NULL,
surname VARCHAR(80) NOT NULL,
name VARCHAR(50) NOT NULL,
patronymic VARCHAR(60),
spec_ss_id NUMBER NOT NULL,
experience NUMBER NOT NULL,
PRIMARY KEY (ss_id),
FOREIGN KEY (spec_ss_id) REFERENCES spec_of_service_staff (spec_ss_id)
)