
(ns site.karavancentrum-hu.pages.vehicle-page.frontend.views
    (:require [app.contents.frontend.api                                 :as contents]
              [re-frame.api                                              :as r]
              [site.components.frontend.api                              :as components]
              [site.karavancentrum-hu.components.api                     :as site.components]
              [site.karavancentrum-hu.pages.vehicle-page.frontend.slider :as slider]
              [vector.api                                                :as vector]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn slideshow
  ; @param (namespaced map) vehicle-item
  [{:vehicle/keys [images]}]
  [:div#kc-vehicle-page--slider
   [slider/view (map (fn [src] ^{:key src}
                                [:div {:style {:align-self "center"}}
                                      [:img {:src src}]])
                     (vector/->items images :media/uri))]])

(defn vehicle-name
  ; @param (namespaced map) vehicle-item
  [{:vehicle/keys [name]}]
  [:h1.kc-section-title name])

(defn vehicle-content
  ; @param (namespaced map) vehicle-item
  [{{:content/keys [body]} :vehicle/description}]
  [:div#kc-vehicle-page--content (contents/parse-content-body body)])

(defn vehicle-view
  ; @param (namespaced map) vehicle-item
  [vehicle-item]
  [:<> [slideshow       vehicle-item]
       [vehicle-name    vehicle-item]
       [vehicle-content vehicle-item]
       [:br]
       [:a {:class :kc-content-button :href "/"} "Vissza a főoldalra"]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn  vehicle-cards
  [vehicles]
  [:div {:id :kc-vehicles--container}
        (letfn [(f [card-list {:vehicle/keys [id] :as vehicle}]
                  (conj card-list [:button {:on-click #(r/dispatch [:x.db/set-item! [:selected-vehicle] id])}
                                           [site.components/vehicle-card vehicle]]))]
               (reduce f [:div {:style {:display "flex" :flex-wrap "wrap" :grid-gap "45px" :justify-content "center"}}] vehicles))])

(defn vehicles-view
  [vehicles]
  (let [selected-vehicle @(r/subscribe [:vehicle-page/get-vehicle])]
       (if selected-vehicle
           [:<> [vehicle-view selected-vehicle]]
           [:<> [vehicle-name (first vehicles)]
                [vehicle-cards vehicles]])))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn view-structure
  []
  (let [vehicles @(r/subscribe [:vehicle-page/get-all-by-link])]
       [:<> [:main {:id :kc-vehicle-page--wrapper}
                   (if (= 1 (count vehicles))
                       [vehicle-view (first vehicles)]
                       [vehicles-view vehicles])]
            [:div {:style {:padding "60px 0 15px 0"}}
                  [components/created-by-link {:theme :light}]]]))

;; --------------------------------------------------------------------------
;; --------------------------------------------------------------------------

(defn view
  [_]
  [view-structure])
