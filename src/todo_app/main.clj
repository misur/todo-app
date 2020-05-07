(ns todo-app.main
  (:gen-class)
  (:require
   [todo-app.controller :as ctrl]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.reload :refer [wrap-reload ]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [ring.middleware.nested-params :only [wrap-nested-params]]
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :refer [not-found]]))

(defroutes routes
  (GET "/"  [] ctrl/index-page)
  (GET "/tasks" [] ctrl/get-all-tasks)
  (GET "/users" [] ctrl/get-all-users)
  (POST "/tasks" request (ctrl/insert-new-task request))
  (GET "/users/:id" [id] (ctrl/get-user-by-id id))
  (POST "/users" request (ctrl/insert-new-user request) )
  (not-found "<h1>Page not found 404</h1>"))




;;TODO

(comment
  ;;TODO
  - delete user
  - delete task
  - tasks for user
  - check error on POST method without params)

(def app  (-> #'routes
              wrap-keyword-params
              wrap-params
              wrap-reload))

(defn -main
  [& port-number]
  (let [port (if (nil? port-number) 9000 port-number)]
  (println (str "Start server on port:" port))
  (jetty/run-jetty app
   {:port (Integer. port)})))



