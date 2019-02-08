(defproject org.clojars.flawless/cljs-react-test "0.1.6"
  :description "A ClojureScript wrapper around Reacts Test Utilities"
  :url "https://github.com/bensu/cljs-react-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.516"]
                 [cljsjs/react "16.8.1-0"]
                 [cljsjs/react-dom "16.8.1-0"]
                 [camel-snake-kebab "0.4.0"]]

  :scm {:name "git"
        :url "https://github.com/Flawless/cljs-react-test"}

  :deploy-repositories [["clojars" {:creds :gpg}]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-doo "0.1.10"]]

  :doo {:paths {:karma "./node_modules/karma/bin/karma"}}

  :aliases {"test" ["with-profile" "test" "doo" "chrome-headless" "test"]}

  :source-paths ["src"]

  :profiles {:test {:dependencies [[prismatic/dommy "1.1.0"]
                                   [cljsjs/react-dom "16.8.1-0" :exclusions [cljsjs/react]]]}}

  :cljsbuild {:builds
              {:test {:source-paths ["src" "test"]
                      :compiler {:output-to "target/testable.js"
                                 :output-dir "target"
                                 :main test.test-runner
                                 :source-map true
                                 :optimizations :none
                                 :cache-analysis false
                                 :pretty-print true}}}})
