(defproject cljs-react-test "0.1.0-SNAPSHOT"
  :description "A ClojureScript wrapper around Reacts Test Utilities"
  :url "https://github.com/bensu/cljs-react-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljsjs/react-with-addons "0.12.2-4"]
                 [prismatic/dommy "1.0.0"]
                 [org.omcljs/om "0.8.8" :exclusions [cljsjs/react]]]

  :scm {:name "git"
        :url "https://github.com/bensu/om-syncing"}

  :deploy-repositories [["clojars" {:creds :gpg}]]

  :plugins [[lein-cljsbuild "1.0.4"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["target/*"]
  
  :cljsbuild {
              :builds {:tests {:source-paths ["src" "test"]
                               :notify-command ["phantomjs"
                                                "vendor/phantom/unit-test.js"
                                                "vendor/phantom/unit-test.html"]
                               :compiler {:output-to "target/testable.js"
                                          :optimizations :whitespace
                                          :cache-analysis false
                                          :pretty-print true}}}})
