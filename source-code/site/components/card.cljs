

(ns site.components.card)


(defn card [{:keys [src name] :as props}]
  [:div.vehicle--card 
   [:div.vehicle--card-thumbnail {:style {:background-image (str "url("src")")}}]
   [:p.vehicle--card-name name]])

(defn view [props]
  [card props])
