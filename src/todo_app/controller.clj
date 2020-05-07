(ns todo-app.controller
  (:require
   [todo-app.db-conn :as db]
   [bouncer.core :as b]
   [bouncer.validators :as v]
   [clojure.tools.logging :as log]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.reload :refer [wrap-reload ]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [ring.middleware.nested-params :only [wrap-nested-params]]
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :refer [not-found]]
   [todo-app.db-conn :as db]
   [clojure.data.json :as json]
   [todo-app.web-util :as logg]
   [todo-app.service :as ctrl ]))




(defn web-response
  "Retur web response "
  [code message]
  {:status code
   :body (json/write-str message )
   :headers {"content-type" "application/json"
             "Access-Control-Allow-Origin" "*"
             "Access-Control-Allow-Headers" "Content-Type"}})

(defn get-value-from-request
  "Get data value from http-request"
  [request]
  (json/read-str (slurp (-> request :body)) :key-fn keyword))


(defn index-page
  [request]
  (logg/log request "Index page")
  {:body "<h1>Hello REST todo app</h1>"
   :status 200
   :headers {
             "Access-Control-Allow-Origin" "*"
             "Access-Control-Allow-Headers" "Content-Type"}}) 

(defn get-name
  [request]
  (let [name (get-in request [:route-params :name])]
    {:status 200 
     :body (str "<h1>Hello " name "!!! </h1>")
     :headers {
               "Access-Control-Allow-Origin" "*"
               "Access-Control-Allow-Headers" "Content-Type"}}))

(defn get-all-tasks
  "Get all tasks"
  [request]
  (log/info "Get all tasks")
  (web-response 200 {:tasks  (ctrl/get-all-tasks)}))




(defn get-all-users
  "Get all users form DB"
  [request]
  (println "get all users")
  (web-response 200 {:users  (ctrl/get-all-users)}))

(defn get-user-by-id
  "Get user by id"
  [& id]
  (let [user (ctrl/get-user-by-id id)]
    (log/info user)
    (if (or (nil? id) (nil? user))
      (web-response 404 (str "User does not exist with id: "id))
      (web-response 200 user))))


(defn insert-new-user
  "Insert new user"
  [request]
  (log/info "insert new user")
  (try
    (if (nil?  (-> request :body))
        (web-response 400 "Email and user-name  are required fields.")
    (let [user (ctrl/insert-user (get-value-from-request request))]
      (web-response 200 user)))
    (catch Exception e
      (web-response 400 (.getMessage e)))))

(defn insert-new-task
  "Insert new task WS"
  [request]
  (log/info "Insert new task")
  (try
    (let [task   (get-value-from-request request)]
      (println task)
      (ctrl/insert-new-task (:description task) (:user-id task))
      (web-response 400 task))
    (catch Exception e
      (web-response 400 (.getMessage e)))))

(defn delete-user
  "Delete user by id"
  [& id]
  (log/info (str "Delete user with id: " id))
  )








