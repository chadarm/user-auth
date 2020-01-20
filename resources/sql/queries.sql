
-- :name create-user-table
-- :command :execute
-- :result :raw
-- :doc Create users table
create table users ( 
    username        VARCHAR(30) PRIMARY KEY,
    pass            VARCHAR(30));


-- :name drop-users-table :!
-- :doc Drop users table if exists
drop table if exists users




-- :name sign-up-new-user! :! :n
-- :doc Creates a new user in the database
INSERT INTO users (username, pass)
VALUES (:username, :pass);


-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE username = :username;


-- :name get-users :? :*
-- :doc retrieves a user record given the id
SELECT * FROM users;



-- :name verify-new-user
-- :doc Verify if this user exists in the datbase already





-- :name user-login
-- :doc Get token for user based on login information






