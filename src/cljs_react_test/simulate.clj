(ns cljs-react-test.simulate 
  (:require [camel-snake-kebab.core :refer :all])
  (:refer-clojure :exclude [drop map meta time]))

;; Structure copied from https://github.com/omcljs/om/blob/master/src/om/dom.clj

(def events
  '[onCopy onCut onPaste 
    onKeyDown onKeyPress onKeyUp
    onFocus onBlur
    onChange onInput onSubmit
    onClick onContextMenu onDoubleClick onDrag onDragEnd onDragEnter onDragExit
    onDragLeave onDragOver onDragStart onDrop onMouseDown onMouseEnter 
    onMouseLeave onMouseMove onMouseOut onMouseOver onMouseUp
    onTouchCancel onTouchEnd onTouchMove onTouchStart
    onScroll
    onWheel])

(defn clj-action [event]
  (-> event
    ->kebab-case
    name
    (clojure.string/replace "on-" "")
    symbol))

(defn ^:private gen-sim-inline-fn [tag]
  `(defmacro ~(clj-action tag) [element# data#]
     `(~'~(symbol "js" (str "React.addons.TestUtils.Simulate." (name tag))) 
       ~element# (cljs.core/clj->js ~data#))))

(defmacro ^:private gen-sim-inline-fns []
  `(do
     ~@(clojure.core/map gen-sim-inline-fn events)))

(gen-sim-inline-fns)

(defn ^:private gen-sim-fn [tag]
  `(defn ~(clj-action tag) [element# data#]
     (.apply ~(symbol "js" (str "React.addons.TestUtils.Simulate." (name tag))) 
             nil (cljs.core/into-array (cons element# (cljs.core/clj->js data#))))))

(defmacro ^:private gen-sim-fns []
  `(do
     ~@(clojure.core/map gen-sim-fn events)))
