(ns test.test-runner
  (:require [cljs.test :refer [successful?] :refer-macros [run-tests]]
            [cljs-react-test.basic]))

(enable-console-print!)

(defn runner []
  (if (successful? (run-tests 'cljs-react-test.basic))
    0
    1))
