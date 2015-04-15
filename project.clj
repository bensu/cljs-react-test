(defproject cljs-react-test "0.1.1-SNAPSHOT"
  :description "A ClojureScript wrapper around Reacts Test Utilities"
  :url "https://github.com/bensu/cljs-react-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljsjs/react-with-addons "0.12.2-4"]
                 [prismatic/dommy "1.0.0"]
                 [camel-snake-kebab "0.3.1"]
                 [com.cemerick/clojurescript.test "0.3.3"]
                 [org.omcljs/om "0.8.8" :exclusions [cljsjs/react]]]

  :scm {:name "git"
        :url "https://github.com/bensu/cljs-react-test"}

  :deploy-repositories [["clojars" {:creds :gpg}]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [com.cemerick/clojurescript.test "0.2.3"]]

  :source-paths ["src"]

  :cljsbuild {:builds 
              {:slimer-test {:source-paths ["src" "test"]
                             ;; :notify-command ["slimerjs"
                             ;;                  :runner
                             ;;                  "vendor/test/unit-test.html"]
                             :test-commands
                             {"unit-tests" ["slimerjs"
                                            :runner
                                            "target/testable.js"]}
                             :compiler {:output-to "target/testable.js"
                                        :output-dir "target/cljs"
                                        :source-map "target/testable.js.map"
                                        :optimizations :whitespace
                                        :cache-analysis false
                                        :pretty-print true}}
               :phantom-test {:source-paths ["src" "test"]
                              :notify-command ["phantomjs"
                                               "vendor/test/unit-test.js"
                                               "vendor/test/unit-test.html"]
                              :compiler {:output-to "target/testable.js"
                                         :optimizations :whitespace
                                         :cache-analysis false
                                         :pretty-print true}}}})
