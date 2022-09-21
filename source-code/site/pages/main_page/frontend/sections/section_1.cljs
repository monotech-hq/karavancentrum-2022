
(ns site.pages.main-page.frontend.sections.section-1
  (:require
    [reagent.api :refer [lifecycles]]))
;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn scroll-down-icon []
  [:div#scroll-icon
   [:button {:tab-index "-1"
             :on-click #(let [element (.getElementById js/document "section-2")]
                          (.scrollIntoView element
                            (clj->js {:behavior "smooth"
                                      :block    "start"
                                      :inline   "center"})))}
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
