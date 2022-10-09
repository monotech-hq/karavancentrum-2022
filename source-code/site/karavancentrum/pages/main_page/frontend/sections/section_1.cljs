
(ns site.karavancentrum.pages.main-page.frontend.sections.section-1
  (:require
    [reagent.api :refer [lifecycles]]
    [utils.api :as utils]))

;; --------------------
;; ---- Components ----

(defn scroll-down-icon []
  [:div#scroll-icon
   [:button {:tab-index "-1"
             :on-click #(utils/scroll-into "section-2")}
    [:span]]])

(defn hero []
  (lifecycles
    {:component-did-mount (fn []
                            (let [vh (* (.-innerHeight js/window) 0.01)]
                              (.setProperty (-> js/document .-documentElement .-style)
                                            "--vh"
                                            (str vh  "px"))));
     :reagent-render
     (fn []
       [:div#hero
        [:div#header]
        [scroll-down-icon]
        [:div#facebook-link
         {:style {:display "none"}}
         [:i.fab.fa-facebook-square]]])}))

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [:section
   [hero]])
