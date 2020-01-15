(ns auth-user.db.hug-con 
  (:require
      [hugsql.core :as hugsql])) 


;; not sure if this is needed
(def file-location "user-auth/resources/sql/queries.sql")


(hugsql/def-db-fns "sql/queries.sql")

(hugsql/def-sqlvec-fns "sql/queries.sql")