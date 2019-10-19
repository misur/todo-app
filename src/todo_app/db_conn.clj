(ns todo-app.db-conn
  (:require
   [clojure.java.jdbc :as sql]
   [java-jdbc.ddl :as ddl]))

(def db-spec
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//127.0.0.1:8889/todo_app"
   :user "root"
   :password "root"
   :default-time-zone "+1"
   :server-timezone "UTC"})

(defn generate-id
  "Generate id by time stamp"
  []
  (* (quot (System/currentTimeMillis) 1000) (rand-int 10000)))

(defn get-all-users
  "Get all users"
  []
  (sql/query db-spec ["SELECT * FROM  users LIMIT 2"]))


(defn create-users-table
  "Create users table"
  []
  (sql/db-do-commands db-spec
                      (ddl/create-table  :users
                                         [:id :serial "PRIMARY KEY"]
                                         [:user_name "varchar(32)"]
                                         [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn insert-new-user
  "Insert new user"
  []
  (sql/insert! db-spec :users {:user_name "milos"}))

(defn create-tasks-table
  "Create tasks table"
  []
  (sql/db-do-commands db-spec
                      (ddl/create-table :tasks
                                        [:id "BIGINT(20)" "PRIMARY KEY"]
                                        [:descripton "varchar(32)"]
                                        [:deleted :boolean "DEFAULT FALSE"]
                                        [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))


(defn drop-table-by-name
  "Drop a table by name"
  [name]
  (sql/db-do-commands db-spec
                      (ddl/drop-table name)))

(defn insert-new-task
  "Insert new task"
  [description]
  (sql/insert! db-spec :tasks {:id (generate-id) :descripton description}))

























