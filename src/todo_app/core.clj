(ns todo-app.core
  (:require
   [clojure.java.io :as io]
   [clojure.data.json :as json]
   [clojure.string :as str])
  (:import (java.util UUID)))

(def todo-list (atom '()))

(def task-file "test.txt")

(defn generate-id
  []
  (.toString (UUID/randomUUID)))

(defn add-item
  "Added task for the user"
  [description user]
  (swap! todo-list conj {:id (generate-id) :description description :user user :finished false}))

(defn find-by-user
  "Find all tasks by user"
  [user coll]
  (reduce (fn [result element]
            (if (= user (:user element))
              (conj result element)
              result))
          (empty coll)
          coll))

(defn count-by-user
  "Return number of task of user"
  [user coll]
  (count (find-by-user user coll)))

(defn remove-item
  "Remove task from list by description and user"
  [description user]
  (swap! todo-list (fn [s] (remove #(and (= (:description %) description) (= (:user %) user)) s))))

(defn find-task-by-user-and-desc
  "Find a task by  user and description"
  [user desc coll]
  (reduce (fn [result element]
            (if(and (= user (:user element)) (= desc (:description element)) )
              (conj result element)
              result))
          (empty coll)
          coll))

(defn complete-task
  "Set a task as completed"
  [desc user]
(swap! todo-list (fn [list]
                   (map #(if (and (= desc (:description %)) (= user (:user %)) )
                           (assoc % :finished true) %)
                        list))))
(defn save-tasks
  "Save task to the file"
  []
  (spit (io/resource task-file) (str @todo-list)))

(defn read-tasks-from-file
  "Read tasks from the file"
  []
  (reset! todo-list (read-string (slurp (io/resource task-file)))))



; check if file exists 
;(.exists (io/as-file (io/resource "test.txt")))




