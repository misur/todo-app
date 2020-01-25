(ns todo-app.controller
  (:require
   [todo-app.db-conn :as db]
   [bouncer.core :as b]
   [bouncer.validators :as v]))

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
  (if (nil?(first(b/validate user :email v/required :user-name v/required)))
    (let [user-name (:user-name user)
          email (:email user)
          first-name (:first-name user)
          last-name (:last-name user)]
      (db/insert-user user-name email first-name last-name))
    (throw (Exception. (str(first (b/validate user :email v/required :user-name v/required)))))))
