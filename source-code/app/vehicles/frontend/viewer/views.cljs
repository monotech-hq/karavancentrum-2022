
(ns app.vehicles.frontend.viewer.views
    (:require [app.common.frontend.api :as common]
              ;; TEMP
              [app.contents.frontend.picker.views :as contents]

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
                       {:color               :default
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
                       {:color     :default
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
                            :height        :2xl
                            :indent        {:top :xxs :vertical :xs}
                            :uri           vehicle-thumbnail
                            :width         :4xl}]))

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
                       {:color               :default
                        :content             :number-of-seats
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-number-of-seats-value
  []
  (let [viewer-disabled?        @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-number-of-seats @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :number-of-seats]])]
       [elements/label ::vehicle-number-of-seats-value
                       {:color               :muted
                        :disabled?           viewer-disabled?
                        :content             vehicle-number-of-seats
                        :horizontal-position :left
                        :indent              {:vertical :xs}
                        :min-width           :xxs
                        :placeholder         "-"
                        :selectable?         true}]))

(defn- vehicle-number-of-bunks-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-number-of-bunks-label
                       {:color               :default
                        :content             :number-of-bunks
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-number-of-bunks-value
  []
  (let [viewer-disabled?        @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-number-of-bunks @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :number-of-bunks]])]
       [elements/label ::vehicle-number-of-bunks-value
                       {:color               :muted
                        :disabled?           viewer-disabled?
                        :content             vehicle-number-of-bunks
                        :horizontal-position :left
                        :indent              {:vertical :xs}
                        :min-width           :xxs
                        :placeholder         "-"
                        :selectable?         true}]))

(defn- vehicle-type-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-type-label
                       {:color               :default
                        :content             :type
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-type-value
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-type     @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :type]])]
       [elements/label ::vehicle-type-value
                       {:color               :muted
                        :disabled?           viewer-disabled?
                        :content             vehicle-type
                        :horizontal-position :left
                        :indent              {:vertical :xs}
                        :min-width           :xxs
                        :placeholder         "-"
                        :selectable?         true}]))

(defn- vehicle-construction-year-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-construction-year-label
                       {:color               :default
                        :content             :construction-year
                        :disabled?           viewer-disabled?
                        :horizontal-position :left
                        :indent              {:vertical :xs :top :l}}]))

(defn- vehicle-construction-year-value
  []
  (let [viewer-disabled?          @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-construction-year @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :construction-year]])]
       [elements/label ::vehicle-construction-year-value
                       {:color               :muted
                        :disabled?           viewer-disabled?
                        :content             vehicle-construction-year
                        :horizontal-position :left
                        :indent              {:vertical :xs}
                        :min-width           :xxs
                        :placeholder         "-"
                        :selectable?         true}]))

(defn- vehicle-visibility-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-visibility-label
                       {:content   :vehicle-visibility
                        :disabled? viewer-disabled?
                        :indent    {:top :l :vertical :xs}}]))

(defn- vehicle-visibility-value
  []
  (let [viewer-disabled?   @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        content-visibility @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :visibility]])]
       [elements/label ::vehicle-visibility-value
                       {:color     :muted
                        :content   (case content-visibility :public :public-content :private :private-content)
                        :disabled? viewer-disabled?
                        :indent    {:vertical :xs}}]))

(defn- vehicle-content-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])]
       [elements/label ::vehicle-content-label
                       {:content   :vehicle-content
                        :disabled? viewer-disabled?
                        :indent    {:top :l :vertical :xs}}]))

(defn- vehicle-content-value
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        content-body     @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :content]])]
        ;; TEMP 
       [contents/content-picker-preview ::vehicle-content-body-value
                      {:value-path  [:vehicles :vehicle-viewer/viewed-item :content]}]))

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
                   [vehicle-number-of-bunks-value]]
             [:div (forms/form-block-attributes {:ratio 100})
                   [vehicle-visibility-label]
                   [vehicle-visibility-value]]
             [:div (forms/form-block-attributes {:ratio 100})
                   [vehicle-content-label]
                   [vehicle-content-value]]]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-viewer-ghost-view :vehicles.vehicle-viewer
                                 {}])

(defn- menu-bar
  []
  [common/item-viewer-menu-bar :vehicles.vehicle-viewer
                               {:menu-items [{:label :overview}
                                             {:label :images}]}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :vehicles.vehicle-viewer])]
       (case current-view-id :overview [vehicle-overview]
                             :images   [vehicle-images])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-id       @(a/subscribe [:router/get-current-route-path-param :item-id])
        edit-item-uri    (str "/@app-home/vehicles/"vehicle-id"/edit")]
       [common/item-viewer-control-bar :vehicles.vehicle-viewer
                                       {:disabled?     viewer-disabled?
                                        :edit-item-uri edit-item-uri}]))

(defn- breadcrumbs
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-name     @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :name]])]
       [common/surface-breadcrumbs :vehicles.vehicle-viewer/view
                                   {:crumbs [{:label :app-home    :route "/@app-home"}
                                             {:label :vehicles    :route "/@app-home/vehicles"}
                                             {:label vehicle-name :placeholder :unnamed-vehicle}]
                                    :disabled? viewer-disabled?}]))

(defn- label-bar
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :vehicles.vehicle-viewer])
        vehicle-name     @(a/subscribe [:db/get-item [:vehicles :vehicle-viewer/viewed-item :name]])]
       [common/surface-label :vehicles.vehicle-viewer/view
                             {:disabled?   viewer-disabled?
                              :label       vehicle-name
                              :placeholder :unnamed-vehicle}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [label-bar]
        [breadcrumbs]
        [elements/horizontal-separator {:size :xxl}]
        [menu-bar]
        [view-selector]
        [elements/horizontal-separator {:size :xxl}]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [control-bar]]])

(defn- vehicle-viewer
  []
  [item-viewer/body :vehicles.vehicle-viewer
                    {:auto-title?   true
                     :ghost-element #'ghost-view
                     :item-element  #'view-structure
                     :item-path     [:vehicles :vehicle-viewer/viewed-item]
                     :label-key     :name}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'vehicle-viewer}])
