

(ns site.karavancentrum.components.card)

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn card [{:vehicle/keys [number-of-bunks number-of-seats name thumbnail] :as props}]
  [:div.kc-vehicle-card
   [:div.kc-vehicle-card--thumbnail {:style {:background-image (str "url("thumbnail")")}}]
   [:div.kc-vehicle-card--header [:p.kc-vehicle-card--name name]
                                 [:div.kc-vehicle-card--details
                                    (if (> number-of-seats 0) [:div.kc-vehicle-card--number-of-seats number-of-seats])
                                    (if (> number-of-bunks 0) [:div.kc-vehicle-card--number-of-bunks number-of-bunks])]]])

(defn view [props]
  [card props])
