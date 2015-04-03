(defproject cljs-react-test "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126"]
                 [figwheel "0.2.5"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljsjs/react-with-addons "0.12.2-4"]
                 [prismatic/dommy "1.0.0"]
                 [org.omcljs/om "0.8.8" :exclusions [cljsjs/react]]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-figwheel "0.2.5-SNAPSHOT"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["target/*"]
  
  :cljsbuild {
              :builds {:dev {:source-paths ["src" "dev_src"]
                             :compiler {:output-to "resources/public/js/compiled/cljs_react_test.js"
                                        :output-dir "resources/public/js/compiled/out"
                                        :optimizations :none
                                        :main cljs-react-test.dev
                                        :asset-path "js/compiled/out"
                                        :source-map true
                                        :source-map-timestamp true
                                        :cache-analysis true }}
                       :tests {:source-paths ["src" "test"]
                               :notify-command ["phantomjs"
                                                "vendor/phantom/unit-test.js"
                                                "vendor/phantom/unit-test.html"]
                               :compiler {:output-to "target/testable.js"
                                          :optimizations :whitespace
                                          :cache-analysis false
                                          :pretty-print true}}}}

  :figwheel {
             :http-server-root "public" ;; default and assumes "resources" 
             :server-port 3449          ;; default
             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log" 
             })
