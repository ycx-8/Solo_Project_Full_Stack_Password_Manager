# Phase 1

## Description
- Users can safely store and perform CRUD operations on their passwords.
- Passwords are stored in a remote encrypted server.
- Users need to log in with 2FA and master password to access their stored information and to be able to perform CRUD operations
- Unauthorized users will not perform any actions in the system.
- Additional frontend features like auto-logout, password strength checker, random password generator and data masking
- Additional backend features: rate limiting (in memory, with redis), zero knowledge architecture (the server never has access to the unencrypted data. All encryption and decryption happen at client-side (javax.crypto and AES in React layer))
- Architecture: API first MVC Zero-Knowledge

## JPA ERD (dbdiagram.io syntax)
Table user {
  id INT [PRIMARY KEY]
  account_email VARCHAR(255)
  hashed_login_password VARCHAR(255)
}

Table master_password {
  id INT [PRIMARY KEY]
  hashed_master_password VARCHAR(255)
  last_master_password_change_date DATETIME
  user_id INT
}

Table password_stored {
  id INT [PRIMARY KEY]
  service_name VARCHAR(100)
  hashed_username VARCHAR(255)
  hashed_password VARCHAR(255)
  user_id INT
}

Table audit_record {
  id INT [PRIMARY KEY]
  user_id INT
  action_type VARCHAR
  timestamp DATETIME
}

Ref: password_stored.user_id > user.id // many-to-one
Ref: audit_record.user_id > user.id // many-to-one
Ref: user.id - master_password.user_id // one-to-one

## Entity classes
User, PasswordStored, AuditRecord, MasterPassword

## TODO
1. Entity relationship diagram using an online DB diagram mapper
2. Create entity classes
3. Create relationships (annotations) within entity classes
4. Generate dummy data
5. Provide description of the project