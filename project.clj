(defproject chatings "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[selmer "1.12.18"]
                 [markdown-clj "1.10.1"]
                 [environ "1.0.1"]
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
                 [othello "0.3.0-SNAPSHOT"]
                 [com.carouselapps/to-jdbc-uri "0.5.0"]
                 [ajchemist/boot-figwheel "0.5.14-SNAPSHOT"]]
  :min-lein-version "2.0.0"
  :uberjar-name "chatings.jar"
  :jvm-opts ["-server"]
  :source-paths ["src/clj"]

  :main chatings.core
  :migratus {:store :database}

  ;START:cljsbuild
  :plugins [[lein-environ "1.1.0"]
            [migratus-lein "0.7.3"]
            [lein-cljsbuild "1.1.7"]]
  :resource-paths ["resources" "target/cljsbuild"]
  :cljsbuild
  {:build {:app {:source-paths ["src-cljs"]
                 :compiler     {:output-to     "target/cljsbuild/public/js/app.js"
                                :output-dir    "target/cljsbuild/public/js/out"
                                :main          "chatings.core"
                                :asset-path    "/js/out"
                                :optimizations :none
                                :source-map    true
                                :pretty-print  true}}}}
  :clean-targets
  ^{:protect false}
  [:target-path
   [:cljsbuild :build :app :compiler :output-dir]
   [:cljsbuild :build :app :compiler :output-to]]
  ;END:cljsbuild

  :profiles
  {:uberjar       {:omit-source  true
                   :env          {:production true}
                   :aot          :all
                   :source-paths ["env/prod/clj"]}
   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   ;START:dev-profile
   :project/dev   {:dependencies [[prone "1.0.1"]
                                  [ring/ring-mock "0.3.0"]
                                  [ring/ring-devel "1.4.0"]
                                  [pjstadig/humane-test-output "0.7.0"]
                                  [mvxcvi/puget "1.0.0"]]
                   :source-paths ["env/dev/clj"]
                   :repl-options {:init-ns guestbook.core}
                   :injections   [(require 'pjstadig.humane-test-output)
                                  (pjstadig.humane-test-output/activate!)]
                   ;; when :nrepl-port is set the application
                   ;; starts the nREPL server on load
                   :env          {:dev        true
                                  :port       3000
                                  :nrepl-port 7000
                                  :log-level  :trace}}
   ;END:dev-profile

   :project/test  {:env {:test       true
                         :port       3001
                         :nrepl-port 7001
                         :log-level  :trace}}
   :profiles/dev  {}
   :profiles/test {}})
