
(ns site.pages.main-page.frontend.sections.section-4
  (:require
    [x.app-core.api :as a]
    [mid-fruits.css :as css]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn brand [{:keys [logo label link] :as props}]
  [:div.brand--container
   [:div.brand--logo {:style {:background-image (css/url logo)}}]
   [:p.brand--label label]
   [:a.link.effect--underline.brand--link {:href link} link]])

(defn brands []
  (let [data @(a/subscribe [:db/get-item [:contents :more-brands]])]
    [:div#brands--container
     (map brand data)]))

(defn content []
  [:div#section-4--content
   [brands]])

(defn section-4 []
  [:section#section-4
   [content]])

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [section-4])
