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
                 [ring "1.8.0"]
                 [compojure "1.6.1"]
                 [ring/ring-mock "0.3.2"]
                 [ring/ring-core "1.8.0"]
                 [ring-logger "1.0.1"]
                 [clj-time "0.15.2"]
                 [bouncer "1.0.1"]
                 [metosin/ring-swagger "0.26.2"]
                 [metosin/compojure-api "1.1.13"]]
  :main ^:skip-aot  todo-app.main
  :ring {:handler todo-app.swagger/app
         :auto-reload? true
         :auto-refresh false}
  :uberjar-name "todo.jar"
  :aot [todo-app.main]
  :profiles {:production {:env {:production true}}
             :uberjar {:aot :all}
             :dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                  [cheshire "5.5.0"]
                                  [ring/ring-mock "0.3.0"]]
                   :plugins [[lein-ring "0.12.0"]]}})
