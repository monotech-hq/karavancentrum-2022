
(ns site.karavancentrum.pages.rent-informations.frontend.views
    (:require [app.common.frontend.api   :as common]
              [app.contents.frontend.api :as contents]
              [re-frame.api              :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn view-structure []
  (let [rent-informations @(r/subscribe [:db/get-item [:site :content :rent-informations]])]
       [:<> [:main {:id :kc-rent-informations--wrapper}
                   [:h1.kc-section-title "Bérlési feltételek"]
                   [:div#kc-rent-informations--content [contents/content-preview {:items [rent-informations]}]]]
            [:div {:style {:padding "60px 0 15px 0"}}
                  [common/credits {:theme :light}]]]))

;; --------------------------------------------------------------------------
;; --------------------------------------------------------------------------

(defn view [_]
  [view-structure])
