(ns test.test-runner
  (:require [cljs.test :as tt]
            [doo.runner :refer-macros [doo-tests]]
            [cljs-react-test.basic]))

(doo-tests 'cljs-react-test.basic)
