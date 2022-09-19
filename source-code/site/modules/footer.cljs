

(ns site.modules.footer
  (:require
   [x.app-core.api :as a]
   [x.app-elements.api :refer [anchor]]))

;; -----------------------------------------------------------------------------
;; -- Components ---------------------------------------------------------------

(defn monotech-logo []
  [:div#mt--container
   [:a#mt--link {:href "https://mt.hu/" :target "_blank"}
    [:div#mt--wrapper
     [:p {:style {:color "rgba(155,155,155,0.7)"}} "created by"]
     [:img {:src "logo/mt-logo-dark.png"}]
     [:p {:style {:color "rgba(155,155,155,0.7)"}} "monotech"]]]])

(defn footer-items [items]
  [:div#footer--links-wrapper
   (map-indexed
     (fn [idx item]
       ^{:key (str "Footer-" idx)}
       [:<> item])
     items)])

(defn footer [items]
  [:footer#footer
   [:h2 "Kapcsolat"]
   [footer-items items]
   [monotech-logo]])

(defn view [& items]
  [footer items])

;; -- Components ---------------------------------------------------------------
;; -----------------------------------------------------------------------------
