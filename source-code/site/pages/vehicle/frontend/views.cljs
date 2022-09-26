
(ns site.pages.vehicle.frontend.views
  (:require
    [x.app-core.api :as a]
    [site.components.api :as site.components]
    [site.pages.vehicle.frontend.slider :as slider]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn slideshow [{:vehicle/keys [images]}]
  [:div#vehicle-page--slider {:style {:width "80%" :margin "0px auto"}}
   [slider/view
    (map (fn [src]
           ^{:key src}
           [:div {:style {:align-self "center"}}
            [:img {:src src}]])
         images)]])

(defn title [{:vehicle/keys [name]}]
  [:h1#vehicle-page--title name])

(defn vehicle-view [vehicle]
  [:<>
    [slideshow vehicle]
    [title vehicle]])

(defn back-button []
  [:button {:style {:margin "15px 0"}
            :on-click #(a/dispatch [:vehicle/clear-selected-vehicle!])}
   "Vissza"])

(defn  vehicle-cards [vehicles]
  [:div#vehicles--container
   (map (fn [{:vehicle/keys [id thumbnail name]}]
          ^{:key id}
          [:button {:on-click #(a/dispatch [:db/set-item! [:selected-vehicle] id])}
           [site.components/card {:src  thumbnail
                                  :name name}]])
        vehicles)])


(defn vehicles-view [vehicles]
  (let [selected-vehicle @(a/subscribe [:vehicle/get])]
      (if selected-vehicle
        [:<>
         [back-button]
         [vehicle-view selected-vehicle]]
        [:<>
         [title (first vehicles)]
         [vehicle-cards vehicles]])))

(defn view-structure []
  (let [vehicles         @(a/subscribe [:vehicle/get-all-by-link])]
   [:main#vehicle-page--content
    (if (= 1 (count vehicles))
      [vehicle-view (first vehicles)]
      [vehicles-view vehicles])]))

;; ---- Components ----
;; --------------------------------------------------------------------------

(defn view [_]
  [view-structure])
