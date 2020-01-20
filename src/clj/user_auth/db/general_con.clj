(ns user-auth.db.general-con
  (:require
   [hugsql.core :as hugsql]
   ))


(hugsql/def-db-fns "sql/queries.sql")


(def users-db
  "SQLite database connection spec."
  {:dbtype "sqlite" :dbname "user_db"})

(defn initialize-db
  "Call the hugsql code to initialize the db"
  []
  (create-user-table users-db))


(defn create-new-user-test
  []
  (sign-up-new-user!
   users-db
   {:username "chad"
    :pass "123"}))

(defn get-all-users
  []
  (get-users users-db))


(defn create-new-user
  "Wrapper for the hugsql call. eventually can just allow direct calls to hugsql"
  [username password]
  (sign-up-new-user! users-db username password))




(comment
  
  
  (hugsql/def-sqlvec-fns "sql/queries.sql")
  
  
  (def users-db
    "SQLite database connection spec."
    {:dbtype "sqlite" :dbname "user_db"})

  (create-user-table users-db)
  
  (drop-users-table users-db)

  
  (sign-up-new-user! 
   users-db 
   {:username "chad"
    :pass "123"})

  (hugsql/map-of-db-fns users-db)
  
  
  
  )