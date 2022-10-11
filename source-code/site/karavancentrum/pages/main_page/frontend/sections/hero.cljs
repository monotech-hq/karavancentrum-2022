
(ns site.karavancentrum.pages.main-page.frontend.sections.hero
  (:require [reagent.api :refer [lifecycles]]
            [utils.api   :as utils]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn detect-vh!
  []
  (let [viewport-height (.-innerHeight js/window)
        vh              (* viewport-height 0.01)]
       (.setProperty (-> js/document .-documentElement .-style)
                     (str  "--vh")
                     (str vh "px"))));

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn scroll-down-icon
  []
  [:div#kc-scroll-icon [:button {:tab-index "-1"
                                 :on-click #(utils/scroll-into "renting")}
                                [:span]]])

(defn hero
  []
  (lifecycles
    {:component-did-mount (fn [] (detect-vh!))
     :reagent-render      (fn [] [:div#kc-hero [:div#kc-header]
                                               [scroll-down-icon]])}))

(defn view
  []
  [:section [hero]])
