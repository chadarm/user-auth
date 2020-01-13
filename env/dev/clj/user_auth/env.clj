(ns user-auth.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [user-auth.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[user-auth started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[user-auth has shut down successfully]=-"))
   :middleware wrap-dev})
