(defproject cljs-react-test "0.1.2-SNAPSHOT"
  :description "A ClojureScript wrapper around Reacts Test Utilities"
  :url "https://github.com/bensu/cljs-react-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0-beta3"]
                 [org.clojure/clojurescript "0.0-3269"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljsjs/react-with-addons "0.12.2-4"]
                 [prismatic/dommy "1.0.0"]
                 [camel-snake-kebab "0.3.1"]
                 [org.omcljs/om "0.8.8" :exclusions [cljsjs/react]]]

  :scm {:name "git"
        :url "https://github.com/bensu/cljs-react-test"}

  :deploy-repositories [["clojars" {:creds :gpg}]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-doo "0.1.0-SNAPSHOT"]]

  :source-paths ["src"]
  
  :cljsbuild {:builds 
              {:test {:source-paths ["src" "test"]
                             :compiler {:output-to "target/testable.js"
                                        :output-dir "target/cljs"
                                        :source-map "target/testable.js.map"
                                        :optimizations :whitespace
                                        :cache-analysis false
                                        :pretty-print true}}}})
