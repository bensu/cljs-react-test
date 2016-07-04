(ns cljs-react-test.basic
  (:require [cljs.test :refer-macros [async deftest is testing]]
            [cljsjs.react]
            [cljsjs.react.dom]
            [cljs-react-test.utils :as tu]
            [cljs-react-test.simulate :as sim]
            [dommy.core :as dommy :refer-macros [sel1 sel]]))

(enable-console-print!)

(def ^:dynamic c)

(def app-state (atom "Arya"))

(defn name-input []
  (js/React.createElement
    "div" #js {}
    (js/React.createElement
      "input" #js {"onChange" (fn [e]
                                (reset! app-state (.. e -target -value)))})
    (js/React.createElement
      "p" #js {}
      @app-state)))

(deftest name-component
  (testing "The initial state is displayed"
    (let [c (tu/new-container!)
          _ (js/ReactDOM.render (name-input) c)
          display-node (sel1 c [:p])
          input-node (sel1 c [:input])]
      (is (re-find #"Arya" (.-innerHTML display-node)))
      (testing "and when there is new input, it changes the state"
        (sim/change input-node {:target {:value "Nymeria"}})
        (js/ReactDOM.render (name-input) c)
        (is (= "Nymeria" @app-state))
        (is (re-find #"Nymeria" (.-innerHTML display-node))))
      (tu/unmount! c))))

(def button-state (atom true))

(defn button []
  (js/React.createElement
    "div" #js {}
    (js/React.createElement
      "p" #js {} "My answer is:" (if @button-state "Yes" "No"))
    (js/React.createElement
      "button" #js {"onClick" (fn [_] (swap! button-state not))} "Toggle")))

(deftest bool-component
  (testing "The inital state is displayed"
    (let [c (tu/new-container!)
          _ (js/ReactDOM.render (button) c)
          display-node (sel1 c [:p])
          input-node (sel1 c [:button])]
      (is (re-find #"Yes" (.-innerHTML display-node)))
      (testing "and it changes after a click"
        (sim/click input-node nil)
        (js/ReactDOM.render (button) c)
        (is (false? @button-state))
        (is (re-find #"No" (.-innerHTML display-node))))
      (tu/unmount! c))))
