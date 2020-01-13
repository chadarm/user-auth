(ns user-auth.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[user-auth started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[user-auth has shut down successfully]=-"))
   :middleware identity})
