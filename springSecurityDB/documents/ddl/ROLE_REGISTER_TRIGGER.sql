CREATE TRIGGER role_register
AFTER INSERT ON user_account

FOR EACH ROW
	BEGIN
		INSERT INTO
			authorities		
		SET
			user_id = NEW.user_id,
			authority = 'ROLE_USER'
;

END;