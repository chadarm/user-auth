
-- :name create-user-table
-- :command :execute
-- :result :raw
-- :doc Create users table
create table users (
    id              VARCHAR(20) PRIMARY KEY,
    username        VARCHAR(30),
    pass            VARCHAR(30));







-- :name sign-up-new-user
-- :doc Creates a new user in the database
INSERT INTO users
(username, pass)
VALUES (:username, :pass)




-- :name verify-new-user
-- :doc Verify if this user exists in the datbase already





-- :name user-login
-- :doc Get token for user based on login information






-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id
