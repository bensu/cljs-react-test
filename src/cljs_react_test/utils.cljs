(ns cljs-react-test.utils
  "Provides common testing functions"
  (:require [cljsjs.react]))

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

(def TestUtils js/React.addons.TestUtils)

(defn render-in-dom [react-element]
  (.renderIntoDocument TestUtils react-element))

(defn mock-component [component mock-name]
  (.mockComponent TestUtils component mock-name))

(defn react-element? [react-element]
  (.isElement TestUtils react-element))

(defn react-isa? [react-element react-class]
  (.isElementOfType TestUtils react-element react-class))

(defn dom-component? [react-component]
  (.isDOMComponent TestUtils react-component))

(defn composite? [react-component]
  (.isCompositeComponent TestUtils react-component))

(defn composite-with-type? [react-component react-class]
  (.isCompositeComponentWithType TestUtils react-component react-class))

(defn find-component-by [react-tree pred]
  (.findAllInRenderedTree TestUtils react-tree pred))

(defn find-by-class [react-tree class-name]
  (.scryRenderedDOMComponentsWithClass TestUtils react-tree class-name))

(defn find-one-by-class [react-tree class-name]
  (.findRenderedDOMComponentWithClass TestUtils react-tree class-name))

(defn find-by-tag [react-tree tag]
  (.scryRenderedDOMComponentsWithTag TestUtils react-tree tag))

(defn find-one-by-tag [react-tree tag]
  (.findRenderedDOMComponentWithTag TestUtils react-tree tag))

(defn find-by-type [react-tree type]
  (.scryRenderedComponentsWithType TestUtils react-tree type))

(defn find-one-by-type [react-tree type]
  (.findRenderedComponentWithType TestUtils react-tree type))
