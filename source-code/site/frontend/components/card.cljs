

(ns site.frontend.components.card)

;; -----------------------------------------------------------------------------
;; -- Prototypes ---------------------------------------------------------------

(defn card-props-prototype [props]
  (dissoc props :href :src :title))

;; -- Prototypes ---------------------------------------------------------------
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; -- Components ---------------------------------------------------------------

(defn card [{:keys [href src title] :as props}]
  [:div.card (card-props-prototype props)
   [:a {:href href}
    [:div
     [:img {:src src}]]
    [:p title]]])

(defn view [props]
  [card props])
