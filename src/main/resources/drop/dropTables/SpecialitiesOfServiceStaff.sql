BEGIN EXECUTE IMMEDIATE 'DROP TABLE spec_of_service_staff'; EXCEPTION   WHEN OTHERS THEN      IF SQLCODE != -942 THEN         RAISE; END IF; END;