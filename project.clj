(defproject todo-app "0.1.0-SNAPSHOT"
  :description "Web todo application"
  :url "http://misur.me"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.1"]
                 [org.clojure/tools.logging "0.5.0"]
                 [org.clojure/java.jdbc "0.7.10"]
                 [mysql/mysql-connector-java "5.1.39"]
                 [java-jdbc/dsl "0.1.0"]
                 [ring "1.7.1"]
                 [compojure "1.6.1"]
                 [ring/ring-mock "0.3.2"]
                 [ring-logger "1.0.1"]
                 [clj-time "0.15.2"]]
  :main ^:skip-aot todo-app.main
  :ring {:handler todo-app.main/app
         :auto-reload? true
         :auto-refresh false}
  :profiles {:dev
             {:main todo-app.main/-dev-main}})
