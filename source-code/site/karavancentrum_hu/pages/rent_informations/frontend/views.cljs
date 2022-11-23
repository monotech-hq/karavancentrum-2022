
(ns site.karavancentrum-hu.pages.rent-informations.frontend.views
    (:require [app.contents.frontend.api    :as contents]
              [re-frame.api                 :as r]
              [site.components.frontend.api :as components]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn view-structure []
  (if-let [rent-informations @(r/subscribe [:x.db/get-applied-item [:site :website-content :rent-informations]
                                                                   contents/parse-content-body])]
          [:<> [:main {:id :kc-rent-informations--wrapper}
                      [:h1.kc-section-title "Bérlési feltételek"]
                      [:div#kc-rent-informations--content rent-informations]
                      [:br]
                      [:a {:class :kc-content-button :href "/"} "Vissza a főoldalra"]]
               [:div {:style {:padding "60px 0 15px 0"}}
                     [components/credits {:theme :light}]]]))

;; --------------------------------------------------------------------------
;; --------------------------------------------------------------------------

(defn view [_]
  [view-structure])
