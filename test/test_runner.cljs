(ns test.test-runner
  (:refer-clojure :exclude (set-print-fn!)) 
  (:require [cljs.test :as t :refer-macros [run-tests]]
            [cemerick.cljs.test]
            [cljs-react-test.basic]))

(enable-console-print!)

(defn runner [] 
  (run-tests
    (t/empty-env :t/default)
    'cljs-react-test.basic))

(defn ^:export set-print-fn! [f]
  (set! cljs.core.*print-fn* f))
