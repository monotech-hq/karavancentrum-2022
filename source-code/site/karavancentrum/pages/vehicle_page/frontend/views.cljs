
(ns site.karavancentrum.pages.vehicle-page.frontend.views
    (:require [app.common.frontend.api                                :as common]
              [app.contents.frontend.api                              :as contents]
              [re-frame.api                                           :as r]
              [site.karavancentrum.components.api                     :as site.components]
              [site.karavancentrum.pages.vehicle-page.frontend.slider :as slider]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn slideshow [{:vehicle/keys [images]}]
  [:div#kc-vehicle-page--slider
   [slider/view (map (fn [src] ^{:key src}
                                [:div {:style {:align-self "center"}}
                                      [:img {:src src}]])
                     images)]])

(defn vehicle-name
  [{:vehicle/keys [name]}]
  [:h1.kc-section-title name])

(defn vehicle-content
  [{:vehicle/keys [description]}]
  [:div#kc-vehicle-page--content [contents/content-preview {:item-id (:content/id description)}]])

(defn vehicle-view
  [vehicle]
  [:<> [slideshow       vehicle]
       [vehicle-name    vehicle]
       [vehicle-content vehicle]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn  vehicle-cards
  [vehicles]
  [:div#kc-vehicles--container
   (map (fn [{:vehicle/keys [id thumbnail name]}]
          ^{:key id}
           [:button {:on-click #(r/dispatch [:db/set-item! [:selected-vehicle] id])}
                    [site.components/card {:src thumbnail :name name}]])
        vehicles)])

(defn vehicles-view
  [vehicles]
  (let [selected-vehicle @(r/subscribe [:vehicle-page/get-vehicle])]
       (if selected-vehicle
           [:<> [vehicle-view selected-vehicle]]
           [:<> [vehicle-name (first vehicles)]
                [vehicle-cards vehicles]])))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn view-structure []
  (let [vehicles @(r/subscribe [:vehicle-page/get-all-by-link])]
       [:<> [:main {:id :kc-vehicle-page--wrapper}
                   (if (= 1 (count vehicles))
                       [vehicle-view (first vehicles)]
                       [vehicles-view vehicles])]
            [:div {:style {:padding "60px 0 15px 0"}}
                  [common/credits {:theme :light}]]]))

;; --------------------------------------------------------------------------
;; --------------------------------------------------------------------------

(defn view [_]
  [view-structure])
