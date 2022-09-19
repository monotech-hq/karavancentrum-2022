
(ns site.pages.main-page.frontend.sections.section-1)

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn scroll-down-icon []
  [:div#scroll-icon
   [:button {:on-click #(let [element (.getElementById js/document "form")]
                          (.scrollIntoView element
                            (clj->js {:behavior "smooth"
                                      :block    "start"
                                      :inline   "center"})))}
    [:span]]])

(defn hero []
  [:div#hero
   [:div#header]
   [scroll-down-icon]
   [:div#facebook-link
    {:style {:display "none"}}
    [:i.fab.fa-facebook-square]]])

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [:section
   [hero]])
