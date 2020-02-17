(ns chatings.routes.home
  (:require
    [chatings.layout :as layout]
    [chatings.db.core :as db]
    [compojure.core :refer [defroutes GET POST]]
    [ring.util.response :refer [response status]]))


(defn home-page []
  (layout/render "home.html"))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
           (GET "/" [] (home-page))
           (GET "/messages" [] (response (db/get-messages)))
           (GET "/about" [] (about-page)))