
(ns site.pages.vehicle.frontend.views
  (:require
    [x.app-core.api :as a]
    [site.pages.vehicle.frontend.slider :as slider]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn slideshow [{:vehicle/keys [images]}]
  [:div {:style {:width "80%" :margin "0px auto"}}
   [slider/view
    (map (fn [src]
           ^{:key src}
           [:div
            [:img {:src src}]])
         images)]])

(defn title [{:vehicle/keys [name]}]
  [:h1#vehicle-page--title name])

(defn vehicle []
  (let [vehicle @(a/subscribe [:vehicle/get])]
   [:main#vehicle-page--content
     [slideshow vehicle]
     [title vehicle]]))
;; ---- Components ----
;; --------------------------------------------------------------------------

(defn view [_]
  [vehicle])
