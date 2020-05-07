(ns todo-app.core-test
  (:require [clojure.test :refer :all]
            [todo-app.core :refer :all]
            [todo-app.main :refer :all]
            [todo-app.db-conn :as db]
            [ring.mock.request :as mock]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))


(deftest your-handler-test
  (is (= (-main (mock/request :get "/tasks"))
         {:status  200
          :headers {"content-type" "text/plain"}
          :body    "<h1>all users</h1>"})))


(deftest test-db
  (testing "Delete user with exception"
    (is (thrown? Exception "Catch exception" (db/delete-user nil)))))


(deftest test-update-task
  (testing "Update test to completed"
    ()))




(deftest test-delete-task
  (testing "Testing delete task"
  (is (= 0 (first (db/delete-task 1331755542934))))
  (is (= 1 (first (db/delete-task 13317555429347))))))

(deftest test-delete-user
  (testing "Testing delete user"
    (let [user-id (:generated_key (first (db/insert-user "milos test" "test-delete" "test delete" "test-delete")))]
      (is (some? user-id))
      (is (= 1 (first (db/delete-user user-id)))))))


(:generated_key (first (db/insert-user "milos test" "tete3" "Ewq3" "Wq3")))

(first (db/update-task-status 242540914608 true))

(db/get-all-users)

(db/get-user-by-id 5989001951066)

(db/delete-user 5989001951066)
