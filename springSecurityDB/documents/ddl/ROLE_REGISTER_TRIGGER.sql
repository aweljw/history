CREATE TRIGGER role_register
AFTER INSERT ON wcg2018.user_account

FOR EACH ROW
	BEGIN
		INSERT INTO
			wcg2018.authorities		
		SET
			user_id = NEW.user_id,
			authority = 'ROLE_USER'
;

END;