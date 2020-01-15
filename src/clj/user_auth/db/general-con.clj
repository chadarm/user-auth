(ns user-auth.db.general-con
  (:require
   [user-auth.db.hug-con :as hug-con]))



(def users-db
  "SQLite database connection spec."
  {:dbtype "sqlite" :dbname "user_db"})

(defn initialize-db
  "Call the hugsql code to initialize the db"
  []
  (hug-con/create-new-user-table users-db))

(defn create-new-user
  "Wrapper for the hugsql call. eventually can just allow direct calls to hugsql"
  [username password]
  (hug-con/sign-up-new-user username password))




; (comment
  
;   ;; This was added to the premade file
;   (hugsql/def-db-fns "sql/queries.sql")
  
  
;   (def users-db
;     "SQLite database connection spec."
;     {:dbtype "sqlite" :dbname "user_db"})

;   (defn initialize-db
;     "Call the hugsql code to initialize the db"
;     []
;     (create-new-user-table users-db))

;   (defn create-new-user
;     "Wrapper for the hugsql call. eventually can just allow direct calls to hugsql"
;     [username password]
;     (sign-up-new-user username password))
  
;   )