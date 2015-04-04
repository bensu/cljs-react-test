(ns cljs-react-test.basic
    (:require [cljs.test :refer-macros [deftest testing is are use-fixtures]]
              [cljs-react-test.utils :as tu]
              [cljs-react-test.simulate :as sim :include-macros true]
              [dommy.core :as dommy :refer-macros [sel1 sel]]
              [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]))

(enable-console-print!)

(defn test-component [data owner]
  (om/component
   (dom/div nil
            (dom/p nil "Enter your name:")
            (dom/input 
             #js {:onChange #(om/update! data :name (.. % -target -value))
                  :value (:name data)})
            (dom/p nil (str "Your name is: " (:name data))))))

(deftest name-component
  (testing "The initial state is displayed"
    (let [c (tu/new-container!)
          app-state (atom {:name "Arya"})
          _ (om/root test-component app-state {:target c})
          display-node (second (sel c [:p]))
          input-node (sel1 c [:input])]
      (is (re-find #"Arya" (.-innerHTML display-node)))
      (testing "and when there is new input, it changes the state"
        (sim/change input-node {:target {:value "Nymeria"}})
        (om.core/render-all)
        (is (= "Nymeria" (:name @app-state)))
        (is (re-find #"Nymeria" (.-innerHTML display-node)))))))


(defn button-component [data owner]
  (om/component
   (dom/div nil
            (dom/p nil "My answer is: " (if (:answer data) "Yes" "No"))
            (dom/button #js {:onClick (fn [_] (om/transact! data :answer not))}
                        "Toggle"))))

(deftest bool-component
  (testing "The inital state is displayed"
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
        (is (re-find #"No" (.-innerHTML display-node)))))))
