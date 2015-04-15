(ns test.test-runner
  (:refer-clojure :exclude (set-print-fn!)) 
  (:require [cemerick.cljs.test :as t] 
            [cljs.test :as tt :refer-macros [run-tests]]
            [cljs-react-test.basic]))

(enable-console-print!)

(defn runner [] 
  (run-tests 'cljs-react-test.basic))

(defn ^:export set-print-fn! [f]
  (set! cljs.core.*print-fn* f))
