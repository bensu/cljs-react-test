(ns cljs-react-test.basic
    (:require [cljs.test :refer-macros [deftest testing is are use-fixtures]]
              [cljs-react-test.utils :as tu]
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
      (is (= "Arya" (re-find #"Arya" (.-innerHTML display-node))))
      (testing "and when there is new input, it changes the state"
        (tu/input input-node "Nymeria")
        (is (= "Nymeria" (:name @app-state)))))))


