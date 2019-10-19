(ns todo-app.datomic
  (:require [datomic.api :as d]
            [clojure.instant :as inst]))

(def uri-db "datomic:free://localhost:4334/todo")

(d/create-database uri-db)

(def conn (d/connect uri-db))

(def schema
  (load-file "resources/datomic/schema.edn"))

(d/transact conn schema)

(defn add-user
  [name email birth-day]
  @(d/transact conn [{:db/id (d/tempid :db.part/user)
                      :user/name name
                      :user/email email
                      :user/birth_day (inst/read-instant-date birth-day)}]))

(defn find-user-id
  [user]
  (ffirst
   (d/q '[:find ?eid
          :in $  ?user
          :where [?eid :user/name ?user]]
        (d/db conn)
        user)))

(defn add-task
  [desc  user]
  (let [task-id (d/tempid :db.part/user)]
    @(d/transact conn [{:db/id task-id
                        :task/description desc
                        :task/completed false }
                       {:db/id  (find-user-id user)
                        :user/tasks task-id}])))

(defn read-users
  []
  (d/q '[:find  ?name ?email ?birthday
         :where
         [_ :user/name ?name]
         [_ :user/email ?email]
         [_ :user/birth_day ?birthday]]
       (d/db conn)))


(defn find-task-for-user
  [user]
  (d/q '[:find ?desc ?completed
         :in $ ?name
         :where
         [?eid :user/name ?name]
         [?eid :user/tasks ?task]
         [?task :task/description ?desc]
         [?task :task/completed ?completed]]
       (d/db conn)
       user))


(add-user "milos" "mis@c.cc" "1990-09-27")

(add-user "dusan" "dusan@c.cc" "1990-05-21")

(add-task "fix the illumination" "milos")

(find-user-id "milos")

(read-users)

(find-task-for-user "milos")  
