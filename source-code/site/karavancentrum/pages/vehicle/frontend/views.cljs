
(ns site.karavancentrum.pages.vehicle.frontend.views
  (:require [app.contents.frontend.api :as contents]
    [re-frame.api :as r]
    [utils.html-parser :refer [html->hiccup]]
    [site.karavancentrum.components.api :as site.karavancentrum.components.frontend]
    [site.karavancentrum.pages.vehicle.frontend.slider :as slider]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn slideshow [{:vehicle/keys [images]}]
  [:div#vehicle-page--slider
   [slider/view
    (map (fn [src]
           ^{:key src}
           [:div {:style {:align-self "center"}}
            [:img {:src src}]])
         images)]
   [:div (str images "xxx")]])

(defn vehicle-name [{:vehicle/keys [name]}]
  [:h1#vehicle-page--title name])

(defn vehicle-content [{:vehicle/keys [description]}]
  [:div#vehicle-page--content
    [contents/content-preview {:item-id (:content/id description)}]
    [:div "xxx"]])

(defn vehicle-view [vehicle]
    [:<> [slideshow vehicle]
         [vehicle-name vehicle]
         [vehicle-content vehicle]])

;; -----------------------------------------------------------------------------
;; ---- Comment ----

(defn back-button []
  [:button {:style {:margin "15px 0"}
            :on-click #(r/dispatch [:vehicle/clear-selected-vehicle!])}
   "Vissza"])

(defn  vehicle-cards [vehicles]
  [:div#vehicles--container
   (map (fn [{:vehicle/keys [id thumbnail name]}]
          ^{:key id}
          [:button {:on-click #(r/dispatch [:db/set-item! [:selected-vehicle] id])}
           [site.karavancentrum.components.frontend/card {:src  thumbnail
                                                          :name name}]])
        vehicles)])


(defn vehicles-view [vehicles]
  (let [selected-vehicle @(r/subscribe [:vehicle/get])]
      (if selected-vehicle
        [:<>
         [back-button]
         [vehicle-view selected-vehicle]]
        [:<>
         [vehicle-name (first vehicles)]
         [vehicle-cards vehicles]])))

;; ---- Comment ----
;; -----------------------------------------------------------------------------

(defn view-structure []
  (let [vehicles @(r/subscribe [:vehicle/get-all-by-link])]
       [:main#vehicle-page--content
          (if (= 1 (count vehicles))
            [vehicle-view (first vehicles)]
            [vehicles-view vehicles])
          [:div "xx"]]))

;; ---- Components ----
;; --------------------------------------------------------------------------

(defn view [_]
  [view-structure])
