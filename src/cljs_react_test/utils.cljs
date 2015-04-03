(ns cljs-react-test.utils
  "Provides common testing functions"
  (:require [cljsjs.react]
            [dommy.core :as dommy :refer-macros [sel sel1]]))

;; React Wrappers
;; ==============

(def TestUtils js/React.addons.TestUtils)

(def Simulate js/React.addons.TestUtils.Simulate)

(defn click
  "Simulates a click event on the DOM node"
  [n]
  (.click Simulate n))

(defn focus [n]
  (.focus Simulate n))

(defn key-down [n key]
  (.keyDown Simulate n #js {:key key}))

(defn on-input [n value]
  (.input Simulate n #js {:value value}))

(defn change [n opts]
  (.change Simulate n opts))

(defn input
  "Replaces the input node value"
  [n value]
  (change n (clj->js {:target {:value value}})))

(defn transact!
  "Applies a function to the value and substitutes the result"
  [n f]
  (let [val (.-value n)
        new-val (f val)]
    (input n new-val)))

(defn unmount
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
