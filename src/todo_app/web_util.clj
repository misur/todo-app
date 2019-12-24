(ns todo-app.web-util
  (:require
   [clojure.tools.logging :as log]))


(defn log
  "Request log"
  [request & text]
  (log/info (str "Method: " (:request-method request) " URL: " (:uri request) " - " text )))


;;(str "Method: " (:request-method request) " URL: " (:uri request) " - " text )

