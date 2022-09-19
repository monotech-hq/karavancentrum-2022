
(ns app.vehicles.frontend.viewer.views
    (:require [app.common.frontend.api :as common]
              [forms.api               :as forms]
              [layouts.surface-a.api   :as surface-a]
              [plugins.item-lister.api :as item-lister]
              [plugins.item-viewer.api :as item-viewer]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-images-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-images-label
                       {:color               :muted
                        :content             :images
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-image-list
  []
  (let [vehicle-images @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :images]])]
       [common/item-viewer-image-list :vehicles.vehicle-viewer
                                      {:images          vehicle-images
                                       :no-images-label :no-images-selected}]))

(defn- vehicle-thumbnail-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-thumbnail-label
                       {:color     :muted
                        :content   :thumbnail
                        :disabled? viewer-disabled?
                        :indent    {:top :l :vertical :xs}}]))

(defn- vehicle-thumbnail
  []
  (let [viewer-disabled?  @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-thumbnail @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :thumbnail]])]
       [elements/thumbnail ::vehicle-thumbnail
                           {:border-radius :s
                            :disabled?     viewer-disabled?
                            :height        :l
                            :indent        {:top :xxs :vertical :xs}
                            :uri           vehicle-thumbnail
                            :width         :xxl}]))

(defn- vehicle-images
  []
  [:<> [vehicle-thumbnail-label]
       [vehicle-thumbnail]
       [vehicle-images-label]
       [vehicle-image-list]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-number-of-seats-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-number-of-seats-label
                       {:color               :muted
                        :content             :number-of-seats
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-number-of-seats-value
  []
  (let [viewer-disabled?        @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-number-of-seats @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :number-of-seats]])]
       [elements/label ::vehicle-number-of-seats-value
                       {:disabled?           viewer-disabled?
                        :content             vehicle-number-of-seats
                        :font-size           :m
                        :horizontal-position :left
                        :indent              {:vertical :xs}
                        :min-width           :xxs
                        :placeholder         "-"
                        :selectable?         true}]))

(defn- vehicle-number-of-bunks-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-number-of-bunks-label
                       {:color               :muted
                        :content             :number-of-bunks
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-number-of-bunks-value
  []
  (let [viewer-disabled?        @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-number-of-bunks @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :number-of-bunks]])]
       [elements/label ::vehicle-number-of-bunks-value
                       {:disabled?           viewer-disabled?
                        :content             vehicle-number-of-bunks
                        :font-size           :m
                        :horizontal-position :left
                        :indent              {:vertical :xs}
                        :min-width           :xxs
                        :placeholder         "-"
                        :selectable?         true}]))

(defn- vehicle-type-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-type-label
                       {:color               :muted
                        :content             :type
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-type-value
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-type     @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :type]])]
       [elements/label ::vehicle-type-value
                       {:disabled?           viewer-disabled?
                        :content             vehicle-type
                        :font-size           :m
                        :horizontal-position :left
                        :indent              {:vertical :xs}
                        :min-width           :xxs
                        :placeholder         "-"
                        :selectable?         true}]))

(defn- vehicle-construction-year-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-construction-year-label
                       {:color               :muted
                        :content             :construction-year
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-construction-year-value
  []
  (let [viewer-disabled?          @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-construction-year @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :construction-year]])]
       [elements/label ::vehicle-construction-year-value
                       {:disabled?           viewer-disabled?
                        :content             vehicle-construction-year
                        :font-size           :m
                        :horizontal-position :left
                        :indent              {:vertical :xs}
                        :min-width           :xxs
                        :placeholder         "-"
                        :selectable?         true}]))

(defn- vehicle-overview
  []
  [:<> [:div (forms/form-row-attributes)
             [:div (forms/form-block-attributes {:ratio 25})
                   [vehicle-type-label]
                   [vehicle-type-value]]
             [:div (forms/form-block-attributes {:ratio 25})
                   [vehicle-construction-year-label]
                   [vehicle-construction-year-value]]
             [:div (forms/form-block-attributes {:ratio 25})
                   [vehicle-number-of-seats-label]
                   [vehicle-number-of-seats-value]]
             [:div (forms/form-block-attributes {:ratio 25})
                   [vehicle-number-of-bunks-label]
                   [vehicle-number-of-bunks-value]]]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-viewer-ghost-view :vehicles.vehicle-viewer
                                 {:padding "0 12px"}])

(defn- menu-bar
  []
  [common/item-viewer-menu-bar :vehicles.vehicle-viewer
                               {:menu-items [{:label :overview :view-id :overview}
                                             {:label :images   :view-id :images}]}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :vehicles.vehicle-viewer])]
       [:<> [menu-bar]
            (case current-view-id :overview [vehicle-overview]
                                  :images   [vehicle-images])]))

(defn- vehicle-viewer
  []
  [item-viewer/body :vehicles.vehicle-viewer
                    {:auto-title?   true
                     :ghost-element #'ghost-view
                     :item-element  #'view-selector
                     :item-path     [:vehicles :vehicle-viewer/viewed-item]
                     :label-key     :name}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  (let [vehicle-name @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :name]])]
       [common/item-viewer-breadcrumbs :vehicles.vehicle-viewer
                                       {:crumbs [{:label :app-home
                                                  :route "/@app-home"}
                                                 {:label :vehicles
                                                  :route "/@app-home/vehicles"}
                                                 {:label       vehicle-name
                                                  :placeholder :unnamed-vehicle}]}]))

(defn- label-bar
  []
  (let [vehicle-name    @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :name]])
        vehicle-id      @(a/subscribe [:router/get-current-route-path-param :item-id])
        edit-item-uri    (str "/@app-home/vehicles/"vehicle-id"/edit")]
       [common/item-viewer-label-bar :vehicles.vehicle-viewer
                                     {:edit-item-uri edit-item-uri
                                      :label         vehicle-name
                                      :placeholder   :unnamed-vehicle}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [vehicle-viewer]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
