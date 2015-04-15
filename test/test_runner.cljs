(ns test.test-runner
  (:refer-clojure :exclude (set-print-fn!)) 
  (:require  
            [cljs.test :as tt]
            [cljs-react-test.basic]))

(enable-console-print!)

(defn runner [] 
  (tt/run-tests
    (tt/empty-env ::tt/default)
    'cljs-react-test.basic))

(defn ^:export set-print-fn! [f]
  (set! cljs.core.*print-fn* f))
