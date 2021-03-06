(ns chatings.config
  (:require [selmer.parser :as parser]
            [taoensso.timbre :as timbre]
            [chatings.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
               (fn []
                 (parser/cache-off!)
                 (timbre/info "\n-=[chatings started successfully using the development profile]=-"))
   :middleware wrap-dev})
