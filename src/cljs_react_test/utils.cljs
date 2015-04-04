(ns cljs-react-test.utils
  "Provides common testing functions"
  (:require [cljsjs.react]
            [dommy.core :as dommy :refer-macros [sel sel1]]))

;; React Wrappers
;; ==============

(def TestUtils js/React.addons.TestUtils)

(defn unmount!
  "Unmounts the React Component at a node"
  [n]
  (.unmountComponentAtNode js/React n))

;; Common to all testing

(defn container-div []
  (let [id (str "container-" (gensym))
        node (.createElement js/document "div")]
    (set! (.-id node) id)
    [node (str "#" id)]))

(defn insert-container! [container]
  (dommy/append! (sel1 js/document :body) container))

(defn new-container! []
  (let [[n s] (container-div)]
    (insert-container! n)
    (sel1 s)))
