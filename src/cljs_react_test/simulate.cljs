(ns cljs-react-test.simulate
  (:require [cljsjs.react])
  (:refer-clojure :exclude [drop])
  (:require-macros [cljs-react-test.simulate :as sim]))

(sim/gen-sim-fns)
