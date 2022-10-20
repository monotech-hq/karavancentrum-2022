
(ns site.karavancentrum.pages.rent-informations.frontend.views
    (:require [app.common.frontend.api   :as common]
              [app.contents.frontend.api :as contents]
              [re-frame.api              :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn view-structure []
  (let [content-link @(r/subscribe [:db/get-item [:site :contents :rent-informations]])]
       [:<> [:main {:id :kc-rent-informations--wrapper}
                   [:h1.kc-section-title "Bérlési feltételek"]
                   [:div#kc-rent-informations--content [contents/content-preview {:items [content-link]}]]]
            [:div {:style {:padding "60px 0 15px 0"}}
                  [common/credits {:theme :light}]]]))

;; --------------------------------------------------------------------------
;; --------------------------------------------------------------------------

(defn view [_]
  [view-structure])
