(ns todo-app.service
  (:require
   [todo-app.db-conn :as db]
   [bouncer.core :as b]
   [bouncer.validators :as v]))

(defn normalize-date
  "Change date"
  [item]
  (let [new-val (:created_at item)
        item-n (dissoc item :created_at)
        result (assoc item-n :created_at (.toString new-val) :id (.toString (:id item)))]
    result))

;;;;;;;;;;;;; users method;;;;;;;;;;

(defn get-all-users
  "Get all users"
  []
  (db/sel-by-tbl-name "users"))

(defn get-user-by-id
  "Get user by id"
  [id]
  (let [user (db/get-by-id id "users")]
    (assoc user :created_at (.toString (:created_at user)) :id (.toString (:id user)))))

(defn insert-user
  "Insert new user"
  [user]
  (if (b/valid? user :email v/required :user-name v/required)
    (let [user-name (:user-name user)
          email (:email user)
          first-name (:first-name user)
          last-name (:last-name user)]
      (db/insert-user user-name email first-name last-name))
    (throw (Exception.  (str "Email and user-name are required fields.")))))


(defn delete-user
  "Delete user by id"
  [id]
  (db/delete-user id))

;;TODO: create update metod for user


;;;;;;;;;;;;;;;;; tasks method ;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn get-all-tasks
  "Get all tasks from DB"
  []
   (db/get-users-with-tasks))

(defn get-task-by-id
  "Get the task by id"
  [id]
  (db/get-by-id id "tasks"))

(defn get-user-tasks
  "Get all user tasks"
  [id])

(defn set-task-as-completed
  "Set task as completed"
  [id])

(defn insert-new-task
  "Insert new task"
  [desc user-id]
  (if (or (nil? desc ) (nil? user-id))
     (throw (Exception.  (str "Description and user id are required fields.")))
    (db/insert-task desc user-id)))


(defn delete-task
  "Delete task by id"
  [id]
  (db/delete-task id))


