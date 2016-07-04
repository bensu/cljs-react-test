# cljs-react-test

## Installing

[![Clojars Project](http://clojars.org/cljs-react-test/latest-version.svg)](http://clojars.org/om-routes)

    (:require [cljs-react-test.simulate :as sim]
              [cljs-react-test.utils :as tu])

It is not trivial to get the dependecies right when adding libraries
that depend on different versions of React (addons vs no addons). The following
very explicit configuration should work:

```clj
(:dependencies [cljs-react-test "0.1.4-SNAPSHOT"]
               [cljsjs/react-with-addons "15.2.0-0"]
               [cljsjs/react-dom "15.2.0-0" :exclusions [cljsjs/react]])
```

## Description

A thin wrapper around
[React.addons.TestUtils](https://facebook.github.io/react/docs/test-utils.html).
It provides convenient access to their Simulate Events in
ClojureScript in a more idiomatic way. All of
[React's Synthetic Events](https://facebook.github.io/react/docs/events.html)
have their corresponding action in kebab-case, removing the "on":

    Simulate.onChange(target, data)
    (change target data)

    Simulate.onDrop(target, data)
    (drop target data)

All arguments can be passed as ClojureScript objects and they will be
converted to JS objects via `clj->js`.

Note: Right now all functions take two arguments, even those that don't
need the second one:

    Simulate.onClick(target)
    (click target) 		;; Will raise a warning
    (click target nil)  ;; Correct Way

This will be corrected in the next version.

It also offers a couple of convenient fixture functions in the
`cljs-react-test.utils` namespace such as `new-container!` and `unmount!`.

## Testing Example:

> This guide uses `om` which is deprecated in favor of `om.next`
> For an example using raw React, look at [`test/basic.cljs`](https://github.com/bensu/cljs-react-test/blob/master/test/basic.cljs)
> in this repository

We will be testing an [Om](https://github.com/omcljs/om) component that
takes a name as input and displays it. We start by requiring
`cljs-react-test.utils`, `cljs-react-test.simulate`, and `dommy.core`,
and our usual namespaces:

```clj
(ns cljs-react-test.basic
    (:require [cljs.test :refer-macros [deftest testing is are use-fixtures]]
              [cljs-react-test.utils :as tu]
              [cljs-react-test.simulate :as sim]
              [dommy.core :as dommy :refer-macros [sel1 sel]]
              [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]))
```

We create a `var` where we will put a DOM object to act as container
for our application and a fixture function that starts it, runs the
tests, and then tears down React's rendering tree:

```clj
(def ^:dynamic c)

(use-fixtures :each (fn [test-fn]
                      (binding [c (tu/new-container!)]
                        (test-fn)
                        (tu/unmount! c))))
```

Note: this fixture will not work with asynchronous tests.

We write the simplest component we can think of:

```clj
(defn test-component [data owner]
  (om/component
   (dom/div nil
            (dom/p nil "Enter your name:")
            (dom/input
             #js {:onChange #(om/update! data :name (.. % -target -value))
                  :value (:name data)})
            (dom/p nil (str "Your name is: " (:name data))))))
```

And then we test it assuming there is a DOM Element at `c`:

```clj
(deftest name-component
  (testing "The initial state is displayed"
    (let [app-state (atom {:name "Arya"})
          _ (om/root test-component app-state {:target c})
          display-node (second (sel c [:p]))
          input-node (sel1 c [:input])]
      (is (re-find #"Arya" (.-innerHTML display-node)))
      (testing "and when there is new input, it changes the state"
        (sim/change input-node {:target {:value "Nymeria"}})
        (om.core/render-all)
        (is (= "Nymeria" (:name @app-state)))
        (is (re-find #"Nymeria" (.-innerHTML display-node)))))))
```

Notice the structure of test:

1. Set up the initial state in `app-state`.
2. Start the application with `om/root` into `c`.
3. Test the initial rendering.
4. Simulate events and then force a re-render with `om/render-all`.
5. Test the changes both in the state and in the rendering tree.
6. Go back to 4

## Run the Tests

First download the repo:

    git clone https://github.com/bensu/cljs-react-test
    cd cljs-react-tests

And then run the tests with [doo](https://github.com/bensu/doo):

    lein with-profile test doo slimer test

Or if you want to use PhantomJS:

    lein with-profile test doo phantom test

Or use the alias defined in `project.clj` which uses SlimerJS:

    lein test

I've had a better experience with SlimerJS than with PhantomJS.

We need to run the tests in a different profile since the library
itself shouldn't depend on `om` but the tests do.

## Contributions

Pull requests, issues, and feedback welcome.

## License

Copyright © 2016 Sebastian Bensusan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
