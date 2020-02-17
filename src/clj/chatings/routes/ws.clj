(ns chatings.routes.ws
  (:require
    [compojure.core :refer [GET POST defroutes]]
    [bouncer.core :as b]
    [bouncer.validators :as v]
    [chatings.db.core :as db]
    [mount.core :refer [defstate]]
    [taoensso.sente :as sente]
    [taoensso.sente.server-adapters.immutant
     :refer [sente-web-server-adapter]]
    [othello.store :as store]
    [othello.operations :as op :refer (defops)]))

(def container
  (-> (store/operation-list)
      (conj (store/operation (defops ::op/ins "g") :id 1))
      (conj (store/operation (defops ::op/ret 1 ::op/ins "o") :id 2 :parent-id 1))
      (conj (store/operation (defops ::op/ret 2 ::op/ins "t") :id 3 :parent-id 2))
      (conj (store/operation (defops ::op/ret 2 ::op/ins "a") :id 4 :parent-id 2))))

;START:socket
(let [connection (sente/make-channel-socket!
                   sente-web-server-adapter
                   {:user-id-fn
                    (fn [ring-req] (get-in ring-req [:params :client-id]))})]
  (def ring-ajax-post (:ajax-post-fn connection))
  (def ring-ajax-get-or-ws-handshake (:ajax-get-or-ws-handshake-fn connection))
  (def ch-chsk (:ch-recv connection))
  (def chsk-send! (:send-fn connection))
  (def connected-uids (:connected-uids connection)))
;END:socket

;START:save-message
(defn validate-message [params]
  (first
    (b/validate
      params
      :name v/required
      :message [v/required [v/min-count 10]])))

(defn save-message! [message]
  (if-let [errors (validate-message message)]
    {:errors errors}
    (do
      (db/save-message! message)
      message)))

(defn handle-message! [{:keys [id client-id ?data]}]
  (println "\n\n+++++++ GOT MESSAGE:" id (keys ?data))
  (when (= id :chatings/add-message)
    (let [response (-> ?data
                       (assoc :timestamp (java.util.Date.))
                       save-message!)]
      (if (:errors response)
        (chsk-send! client-id [:chatings/error response])
        (doseq [uid (:any @connected-uids)]
          (chsk-send! uid [:chatings/add-message response]))))))
;END:save-message

;START:router
(defn stop-router! [stop-fn]
  (when stop-fn (stop-fn)))

(defn start-router! []
  (println "\n\n+++++++ STARTING ROUTER! +++++++\n\n")
  (sente/start-chsk-router! ch-chsk handle-message!))

(defstate router
          :start (start-router!)
          :stop (stop-router! router))
;END:router

;START:defroutes
(defroutes websocket-routes
           (GET "/ws" req (ring-ajax-get-or-ws-handshake req))
           (POST "/ws" req (ring-ajax-post req)))
;END:defroutes