(ns todo-app.core-test
  (:require [clojure.test :refer :all]
            [todo-app.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))



(add-item "fix car" "milos")
(add-item "clear the room" "milos")

(find-by-user "milos" @todo-list)
