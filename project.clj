(defproject todo-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.1"]
                 [org.clojure/java.jdbc "0.7.10"]
                 [mysql/mysql-connector-java "5.1.39"]
                 [java-jdbc/dsl "0.1.0"]
                 [com.datomic/datomic-free "0.9.5703.21"]]
  :repl-options {:init-ns todo-app.core}
  :datomic {:schemas ["resources/datomic" ["schema.edn"]]}
  :profiles {:dev
             {:datomic {:config "resources/datomic/free-transactor-template.properties"
                        :db-uri "datomic:free://localhost:4334/todo"}}})
