(ns chatings.handler
  (:require [compojure.core :refer [defroutes routes wrap-routes]]
            [chatings.layout :refer [error-page]]
            [chatings.routes.home :refer [home-routes]]
    ;START:ws-routes
            [chatings.routes.ws :refer [websocket-routes]]
    ;END:ws-routes
            [chatings.middleware :as middleware]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.3rd-party.rotor :as rotor]
            [selmer.parser :as parser]
            [environ.core :refer [env]]
            [chatings.config :refer [defaults]]
            [mount.core :as mount]))


(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  ;START:logging
  (timbre/merge-config!
    {:level     ((fnil keyword :info) (env :log-level))
     :appenders {:rotor (rotor/rotor-appender
                          {:path     (or (env :log-path) "chatings.log")
                           :max-size (* 512 1024)
                           :backlog  10})}})
  ;END:logging
  (mount/start)
  ((:init defaults)))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (timbre/info "chatings is shutting down...")
  (mount/stop)
  (timbre/info "shutdown complete!"))

;START:app
;START:app-routes
(def app-routes
  (routes
    #'websocket-routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title  "page not found"})))))
;END:app-routes
(def app (middleware/wrap-base #'app-routes))
;END:app