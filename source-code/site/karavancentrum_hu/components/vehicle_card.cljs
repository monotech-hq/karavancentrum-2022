
(ns site.karavancentrum-hu.components.vehicle-card
    (:require [css.api :as css]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-card
  [{:vehicle/keys [number-of-bunks number-of-seats name thumbnail]}]
  [:div.kc-vehicle-card
   [:div.kc-vehicle-card--thumbnail {:style {:background-image (-> thumbnail :media/uri css/url)}}]
   [:div.kc-vehicle-card--header [:p.kc-vehicle-card--name name]
                                 [:div.kc-vehicle-card--details
                                    (if (> number-of-seats 0) [:div.kc-vehicle-card--number-of-seats number-of-seats])
                                    (if (> number-of-bunks 0) [:div.kc-vehicle-card--number-of-bunks number-of-bunks])]]])

(defn view
  [vehicle]
  [vehicle-card vehicle])
