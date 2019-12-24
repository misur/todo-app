(ns todo-app.core-test
  (:require [clojure.test :refer :all]
            [todo-app.core :refer :all]
            [todo-app.main :refer :all]
            [ring.mock.request :as mock]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))



(deftest your-handler-test
  (is (= (-main (mock/request :get "/tasks"))
         {:status  200
          :headers {"content-type" "text/plain"}
          :body    "<h1>all users</h1>"})))

