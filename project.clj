(defproject chattings "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[selmer "1.12.18"]
                 [markdown-clj "1.10.1"]
                 [luminus/config "0.8"]
                 [ring-middleware-format "0.7.4"]
                 [metosin/ring-http-response "0.9.1"]
                 [bouncer "1.0.1"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [org.webjars/bootstrap "4.4.1"]
                 [org.webjars/font-awesome "5.12.0"]
                 [org.webjars.bower/tether "1.4.4"]
                 [org.webjars/jquery "3.4.1"]
                 [com.taoensso/timbre "4.10.0"]
                 [com.taoensso/sente "1.15.0"]
                 [com.taoensso/tower "3.0.2"]
                 [com.taoensso/encore "2.119.0"]
                 [org.clojure/tools.logging "0.5.0"]
                 [compojure "1.6.1"]
                 [ring-webjars "0.2.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring "1.8.0" :exclusions [ring/ring-jetty-adapter]]
                 [mount "0.1.16"]
                 [luminus-nrepl "0.1.6"]
                 [migratus "1.2.7"]
                 [conman "0.8.4"]
                 [com.h2database/h2 "1.4.200"]
                 [org.postgresql/postgresql "42.2.9"]
                 [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
                 [luminus-immutant "0.2.5"]
                 [luminus-log4j "0.1.5"]
                 [cljs-ajax "0.8.0"]
                 [reagent "0.9.1"]
                 [cprop "0.1.15"]
                 [org.clojure/clojurescript "1.10.597" :scope "provided"]
                 [philoskim/debux "0.6.3"]
                 [yesql "0.5.3"]
                 [prone "1.0.1"]
                 [othello "0.3.0-SNAPSHOT"]]
  :min-lein-version "2.0.0"
  :uberjar-name "chattings.jar"
  :jvm-opts ["-server"]
  :source-paths ["src/clj"]

  :main chattins.core
  :migratus {:store :database}

  :repl-options {:init-ns chattings.core})
