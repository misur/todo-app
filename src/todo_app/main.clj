(ns todo-app.main
  (:gen-class)
  (:require
   [clojure.tools.logging :as log]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.reload :refer [wrap-reload]]
   [compojure.core :refer [defroutes GET]]
   [compojure.route :refer [not-found]]
   [todo-app.db-conn :as db]
   [clojure.data.json :as json]
   [ring.logger :as logger]
   [todo-app.web-util :as logg]))


(defn index-page
  [request]
  (logg/log request "Index page")
  {:body "<h1>Hello REST todo app change</h1>"
   :status 200
   :headers {}}) 

(defn get-name
  [request]
  (let [name (get-in request [:route-params :name])]
    {:status 200
     :body (str "<h1>Hello " name "!!! </h1>")
     :headers {}}))

(defn get-all-tasks
  [request]
  (println (str request))
  {:status 200
   :body  (str "<h1> All tasks "  (+ 21 44) "</h1>")
   :headers {}})


(defn normalize-date
  "Change date"
  [item]
  (let [new-val (:created_at item)
        item-n (dissoc item :created_at)
        result (assoc item-n :created_at (.toString new-val))]
    result))


;;TODO: fix eror with converting timestamp to json
(defn get-all-users
  "Get all users form DB"
  [request]
  (println "get all users")
  {:status 200
   :body (json/write-str {:users (mapv  normalize-date (db/get-all-users))})
   :headers {}})

(defroutes app
  (GET "/"  [] index-page)
  (GET "/tasks" [] get-all-tasks)
  (GET "/users" [] get-all-users)
  (not-found "<h1>Page NOT FOUND 404</h1>"))




;;TODO

(defn -main
  [port-number]
  (let [port (if (nil? port-number) 8080 port-number)]
  (println (str "Start server on port:" port))
  (jetty/run-jetty app
   {:port (Integer. port)})))

(defn -dev-main
  [& port-number]
    (let [port (if (nil? port-number) 8080 port-number)]
  (println (str "Start server on port:" port))
  (jetty/run-jetty
   (logger/wrap-with-logger  app)
   {:port (Integer. port)})))




(defn testd
  "test dbg"
  []
  (let [a 10 b 20]
    (def c (+ a 20))
    (println c)))



(testd)



















