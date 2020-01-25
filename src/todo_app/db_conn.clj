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


;;;;;;;;;;;;;;;;;;;; unit method ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn drop-table-by-name
  "Drop a table by name"
  [name]
  (try
    (sql/db-do-commands db-spec
                        (ddl/drop-table name))
    (catch Exception e (str "Exception: " (.getMessage e)))))




(defn sel-by-tbl-name
  "Select all from param name table"
  [name ]
  (try
   (let [tbl-name (if (or (nil? name) (= "" name))
                    "users"
                    name)]
     (sql/query db-spec [(str "SELECT * FROM " tbl-name)]))
   (catch Exception e (str "Exception: " (.getMessage e)))))

(defn update-or-insert! 
  "Updates columns or inserts a new row in the specified table"
  [db table row where-clause]
  (sql/with-db-transaction [db-spec db]
    (if (zero? (first (sql/update! db-spec table row where-clause)))
      (sql/insert! db-spec table row)
      row)))


(defn get-by-id
  "Get by id from param table"
  [id table]
  (first (sql/query db-spec [(str "SELECT * FROM " table " WHERE id=" id)])))


;;;;;;;;;;;;;; users region ;;;;;;;;;;;;;;;;;;;;;;;

(defn get-all-users
  "Get all users"
  []
  (sql/query db-spec ["SELECT * FROM  users"]))


(defn create-users-table
  "Create users table"
  []

  (sql/db-do-commands db-spec
       (ddl/create-table  :users
            [:id :serial "PRIMARY KEY"]
            [:user_name "varchar(32)"]
            [:email "varchar(32) UNIQUE NOT NULL"]
            [:first_name "varchar(32)"]
            [:last_name "varchar(32)"]
            [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn insert-user
  "Insert a new user"
  [user-name email first-name last-name]
  (try
    (sql/insert! db-spec :users
               {:id (generate-id)
                :user_name user-name
                :email email
                :first_name first-name
                :last_name last-name})
    (catch Exception e (str "Exception: " (.getMessage e)))))


(defn get-user-id-by-email
  "Get user id by email"
  [email]
  (:id (sql/query db-spec ["SELECT id FROM users WHERE email = ?" email] {:result-set-fn first})))


(defn get-user-by-email
  "Get user by email"
  [email]
  (sql/query db-spec ["SELECT * FROM users WHERE email = ?" email] {:result-set-fn first}))


(defn get-user-by-id
  "Get user by id"
  [id]
  (sql/query db-spec ["SELECT * FROM users WHERE id = ?" id] {:result-set-fn first}))

(defn update-user
  "Update the user by id"
  [id value]
  (let [user (get-user-by-id id)]
    (if (nil? user)
    (str "The user with id:" id " does not exist.")
    (update-or-insert! db-spec :users value  ["id= ?" id]))))


;;;;;;;;;;;;;;;;; tasks region ;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create-tasks-table
  "Create tasks table"
  []
  (sql/db-do-commands db-spec
     (ddl/create-table :tasks
         [:id :serial "PRIMARY KEY"]
         [:descripton "varchar(32) NOT NULL"]
         [:user_id "BIGINT NOT NULL"]
         [:completed :boolean "DEFAULT FALSE"]
         [:deleted :boolean "DEFAULT FALSE"]
         [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn insert-task
  "Insert a new task"
  [description user-id]
  (try
    (sql/insert! db-spec :tasks
               {:id (generate-id)
                :descripton description
                :user_id user-id})
    (catch Exception e (str "Exception: " (.getMessage e)))))

(defn update-task 
  "Update the task by id"
  [id value]
  (let [task (get-by-id id "tasks")]
    (if (nil? task)
      (str "The task with id: " id " does not exist")
      (update-or-insert! db-spec :tasks value ["id = ? " id ]))))

(defn get-all-task-for-user
  "Get all tasks for user by id"
  [id & completed]
  (if (nil? completed)
    (sql/query db-spec ["SELECT * FROM tasks WHERE user_id=?" id])
    (sql/query db-spec [(str "SELECT * FROM tasks WHERE  user_id=" id " and completed=" completed)])))


(defn get-users-with-tasks
  "Get all users with"
  [& completed]
  (let [users (get-all-users)]
    (if (nil? completed)
      (map #(conj  % {:tasks (get-all-task-for-user(:id %))})users)
      (map #(conj  % {:tasks (get-all-task-for-user(:id %) completed)})users))))






