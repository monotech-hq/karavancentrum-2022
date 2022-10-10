
(ns site.karavancentrum.pages.vehicle.frontend.views
    (:require [app.common.frontend.api                           :as common]
              [app.contents.frontend.api                         :as contents]
              [re-frame.api                                      :as r]
              [site.karavancentrum.components.api                :as site.karavancentrum.components.frontend]
              [site.karavancentrum.pages.vehicle.frontend.slider :as slider]
              [utils.html-parser                                 :refer [html->hiccup]]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn slideshow [{:vehicle/keys [images]}]
  [:div#vehicle-page--slider
   [slider/view (map (fn [src] ^{:key src}
                                [:div {:style {:align-self "center"}}
                                      [:img {:src src}]])
                     images)]])

(defn vehicle-name
  [{:vehicle/keys [name]}]
  [:h1#vehicle-page--title name])

(defn vehicle-content
  [{:vehicle/keys [description]}]
  [:div#vehicle-page--content [contents/content-preview {:item-id (:content/id description)}]])

(defn vehicle-view
  [vehicle]
  [:<> [slideshow vehicle]
       [vehicle-name vehicle]
       [vehicle-content vehicle]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn  vehicle-cards
  [vehicles]
  [:div#vehicles--container
   (map (fn [{:vehicle/keys [id thumbnail name]}]
          ^{:key id}
           [:button {:on-click #(r/dispatch [:db/set-item! [:selected-vehicle] id])}
                    [site.karavancentrum.components.frontend/card {:src thumbnail :name name}]])
        vehicles)])

(defn vehicles-view
  [vehicles]
  (let [selected-vehicle @(r/subscribe [:vehicle/get-vehicle])]
       (if selected-vehicle
           [:<> [vehicle-view selected-vehicle]]
           [:<> [vehicle-name (first vehicles)]
                [vehicle-cards vehicles]])))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn view-structure []
  (let [vehicles @(r/subscribe [:vehicle/get-all-by-link])]
       [:main#vehicle-page--wrapper
          (if (= 1 (count vehicles))
              [vehicle-view (first vehicles)]
              [vehicles-view vehicles])]))

;; --------------------------------------------------------------------------
;; --------------------------------------------------------------------------

(defn view [_]
  [view-structure])
