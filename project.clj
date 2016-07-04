(defproject cljs-react-test "0.1.3"
  :description "A ClojureScript wrapper around Reacts Test Utilities"
  :url "https://github.com/bensu/cljs-react-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3308"]
                 [cljsjs/react-with-addons "15.2.0-0"]
                 [camel-snake-kebab "0.4.0"]]

  :scm {:name "git"
        :url "https://github.com/bensu/cljs-react-test"}

  :deploy-repositories [["clojars" {:creds :gpg}]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-doo "0.1.6"]]

  :aliases {"test" ["with-profile" "test" "doo" "slimer" "test"]}

  :source-paths ["src"]

  :profiles {:test {:dependencies [[prismatic/dommy "1.0.0"]
                                   [cljsjs/react-dom "15.2.0-0" :exclusions [cljsjs/react]]]}}

  :cljsbuild {:builds
              {:test {:source-paths ["src" "test"]
                      :compiler {:output-to "target/testable.js"
                                 :output-dir "target"
                                 :main 'test.test-runner
                                 :source-map "target/testable.js.map"
                                 :optimizations :none
                                 :cache-analysis false
                                 :pretty-print true}}}})
