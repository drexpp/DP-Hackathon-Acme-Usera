start transaction;
	
	use `acme-usera`;
	revoke all privileges on `acme-usera`.* from 'acme-user'@'%';
	revoke all privileges on `acme-usera`.* from 'acme-manager'@'%';
	
	drop user 'acme-user'@'%';
	drop user 'acme-manager'@'%';
	drop database `acme-usera`;
	commit;