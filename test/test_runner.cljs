(ns test.test-runner
  (:require [cemerick.cljs.test :as t :refer-macros [run-tests]] 
            [cljs-react-test.basic]))

(enable-console-print!)

(run-tests 'cljs-react-test.basic)
