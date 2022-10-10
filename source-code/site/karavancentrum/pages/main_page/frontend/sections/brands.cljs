
(ns site.karavancentrum.pages.main-page.frontend.sections.brands
    (:require [mid-fruits.css :as css]
              [re-frame.api   :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn brand-info
  [{:keys [label link description]}]
  [:div [:p.kc-section-title label]])

(defn brand
  [brand-dex {:keys [link-label link description logo title]}]
  [:div.kc-brand {:style (if (odd? brand-dex) {:background-color "rgba(255,255,255,.5)"})}
                 [:p.kc-section-title title]
                 [:div.kc-brand--description description]
                 [:div.kc-brand--card [:div.kc-brand--logo  {:style {:background-image (css/url logo)}}]
                                      [:div.kc-brand--label link-label]
                                      [:div.kc-brand--link  link]
                                      [:div.kc-brand--goto  "Megtekintés"]]])


(defn brands
  []
  (let [brands @(r/subscribe [:db/get-item [:site :contents :brands]])]
       [:div#kc-brands [:div#kc-brands--brand-list (letfn [(f [%1 %2 %3] (conj %1 [brand %2 %3]))]
                                                          (reduce-kv f [:<>] brands))]]))

(defn view
  []
  [brands])
