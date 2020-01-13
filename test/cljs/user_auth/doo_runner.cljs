(ns user-auth.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [user-auth.core-test]))

(doo-tests 'user-auth.core-test)

