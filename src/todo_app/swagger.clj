(ns todo-app.swagger
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]))



(def app
  (api
   {:swagger
    {:ui   "/swagger"
     :spec "/swagger.json"
     :data {:info {:title       "Pizza and Ferries"
                   :description "A simple API using the Compojure Api library"}
            :tags [{:name        "Pizza and Ferries api",
                    :description "All your favourite Pizza, perhaps no ferries though"}]}}}
   (GET "/hello" []
        :query-params [name :- String]
        (println {:message (str "Hello, " name)}))))
