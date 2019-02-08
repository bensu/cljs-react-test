(ns cljs-react-test.utils
  "Provides common testing functions"
  (:require [cljsjs.react.dom.test-utils]
            [cljsjs.react]))

;; General Utils

(defn get-dom-tree [e]
  (let [children (.-children e)]
    (if (zero? (.-length children))
      e
      (into [e] (for [i (range (.-length children))]
                  (get-dom-tree (aget children i)))))))

;; For Fixtures

(defn unmount!
  "Unmounts the React Component at a node"
  [n]
  (.unmountComponentAtNode js/ReactDOM n))

(defn- container-div []
  (let [id (str "container-" (gensym))
        node (.createElement js/document "div")]
    (set! (.-id node) id)
    [node id]))

(defn insert-container! [container]
  (.appendChild (.-body js/document) container))

(defn new-container! []
  (let [[n s] (container-div)]
    (insert-container! n)
    (.getElementById js/document s)))

;; Rest of API for completion's sake:

(defn render-in-dom [react-element]
  (.renderIntoDocument js/ReactTestUtils react-element))

(defn mock-component [component mock-name]
  (.mockComponent js/ReactTestUtils component mock-name))

(defn react-element? [react-element]
  (.isElement js/ReactTestUtils react-element))

(defn react-isa? [react-element react-class]
  (.isElementOfType js/ReactTestUtils react-element react-class))

(defn dom-component? [react-component]
  (.isDOMComponent js/ReactTestUtils react-component))

(defn composite? [react-component]
  (.isCompositeComponent js/ReactTestUtils react-component))

(defn composite-with-type? [react-component react-class]
  (.isCompositeComponentWithType js/ReactTestUtils react-component react-class))

(defn find-component-by [react-tree pred]
  (.findAllInRenderedTree js/ReactTestUtils react-tree pred))

(defn find-by-class [react-tree class-name]
  (.scryRenderedDOMComponentsWithClass js/ReactTestUtils react-tree class-name))

(defn find-one-by-class [react-tree class-name]
  (.findRenderedDOMComponentWithClass js/ReactTestUtils react-tree class-name))

(defn find-by-tag [react-tree tag]
  (.scryRenderedDOMComponentsWithTag js/ReactTestUtils react-tree tag))

(defn find-one-by-tag [react-tree tag]
  (.findRenderedDOMComponentWithTag js/ReactTestUtils react-tree tag))

(defn find-by-type [react-tree type]
  (.scryRenderedComponentsWithType js/ReactTestUtils react-tree type))

(defn find-one-by-type [react-tree type]
  (.findRenderedComponentWithType js/ReactTestUtils react-tree type))
