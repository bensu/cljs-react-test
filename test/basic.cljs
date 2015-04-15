(ns cljs-react-test.basic
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cemerick.cljs.test :as t] 
            [cljs.test :refer-macros [async deftest is testing]]
            [cljs.core.async :as async :refer (<! >! put! chan)]
            [cljs-react-test.utils :as tu]
            [cljs-react-test.simulate :as sim]
            [dommy.core :as dommy :refer-macros [sel1 sel]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def ^:dynamic c)

(defn test-component [data owner]
  (om/component
   (dom/div nil
            (dom/p nil "Enter your name:")
            (dom/input 
             #js {:onChange #(om/update! data :name (.. % -target -value))
                  :value (:name data)})
            (dom/p nil (str "Your name t/is: " (:name data))))))

(deftest name-component
  (testing "The initial state t/is displayed"
    (let [c (tu/new-container!) 
          app-state (atom {:name "Arya"})
          _ (om/root test-component app-state {:target c})
          display-node (second (sel c [:p]))
          input-node (sel1 c [:input])]
      (is (re-find #"Arya" (.-innerHTML display-node)))
      (testing "and when there t/is new input, it changes the state"
        (sim/change input-node {:target {:value "Nymeria"}})
        (om.core/render-all)
        (is (= "Nymeria" (:name @app-state)))
        (is (re-find #"Nymeria" (.-innerHTML display-node))))
      (tu/unmount! c))))

(defn button-component [data owner]
  (om/component
   (dom/div nil
            (dom/p nil "My answer t/is: " (if (:answer data) "Yes" "No"))
            (dom/button #js {:onClick (fn [_] (om/transact! data :answer not))}
                        "Toggle"))))

(deftest bool-component
  (testing "The inital state t/is displayed"
    (let [c (tu/new-container!)
          app-state (atom {:answer true})
          _ (om/root button-component app-state {:target c})
          display-node (sel1 c [:p])
          input-node (sel1 c [:button])]
      (is (re-find #"Yes" (.-innerHTML display-node)))
      (testing "and it changes after a click"
        (sim/click input-node nil)
        (om.core/render-all)
        (is (false? (:answer @app-state)))
        (is (re-find #"No" (.-innerHTML display-node))))
      (tu/unmount! c))))

(defn async-button [data owner opts]
  (reify
    om/IRenderState
    (render-state [_ {:keys [click-ch]}]
      (dom/div nil
        (dom/p nil "My answer is: " (if (:answer data) "Yes" "No"))
        (dom/button #js {:onClick (fn [_] (put! click-ch :click))}
          "Toggle")))))

(deftest async-component
  (testing "The inital state t/is displayed"
    (async done
      (let [c (tu/new-container!)
            app-state (atom {:answer true})
            click-ch (chan)
            _ (om/root async-button app-state {:target c
                                               :init-state {:click-ch click-ch}})
            display-node (sel1 c [:p])
            input-node (sel1 c [:button])]
        (is (re-find #"Yes" (.-innerHTML display-node)))
        (testing "and it changes after a click"
          (sim/click input-node nil)
          (go
            (let [e (<! click-ch)]
              (is (= :click e))
              (tu/unmount! c)
              (done))))))))
