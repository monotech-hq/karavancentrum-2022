
(ns app.vehicles.frontend.editor.views
    (:require [app.common.frontend.api  :as common]
              [app.storage.frontend.api :as storage]
              [forms.api                :as forms]
              [layouts.surface-a.api    :as surface-a]
              [plugins.item-editor.api  :as item-editor]
              [x.app-core.api           :as a]
              [x.app-elements.api       :as elements]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-name-field
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/combo-box ::vehicle-name-field
                           {:autofocus?   true
                            :disabled?    editor-disabled?
                            :indent       {:top :l :vertical :xs}
                            :label        :name
                            :options-path [:vehicles :vehicle-editor/suggestions :name]
                            :value-path   [:vehicles :vehicle-editor/edited-item :name]}]))

(defn- vehicle-construction-year-field
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/text-field ::vehicle-construction-year-field
                            {:disabled?  editor-disabled?
                             :indent     {:top :l :vertical :xs}
                             :label      :construction-year
                             :value-path [:vehicles :vehicle-editor/edited-item :construction-year]}]))

(defn- vehicle-number-of-seats-field
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/text-field ::vehicle-number-of-seats-field
                            {:disabled?  editor-disabled?
                             :indent     {:top :l :vertical :xs}
                             :label      :number-of-seats
                             :value-path [:vehicles :vehicle-editor/edited-item :number-of-seats]}]))

(defn- vehicle-number-of-bunks-field
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/text-field ::vehicle-number-of-bunks-field
                            {:disabled?  editor-disabled?
                             :indent     {:top :l :vertical :xs}
                             :label      :number-of-bunks
                             :value-path [:vehicles :vehicle-editor/edited-item :number-of-bunks]}]))

(defn- vehicle-type-select
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])])
  [elements/select ::vehicle-type-select
                   {:indent        {:top :l :vertical :xs}
                    :label         :type
                    :layout        :select
                    :options-label :vehicle-type
                    :options       [:alcove :semi-integrated :van :caravan :trailer]
                    :value-path    [:vehicles :vehicle-editor/edited-item :type]}])

(defn- vehicle-data
  []
  [:<> [:div (forms/form-row-attributes)
             [:div (forms/form-block-attributes {:ratio 100})
                   [vehicle-name-field]]]
       [:div (forms/form-row-attributes)
             [:div (forms/form-block-attributes {:ratio 50})
                   [vehicle-type-select]]
             [:div (forms/form-block-attributes {:ratio 50})
                   [vehicle-construction-year-field]]]
       [:div (forms/form-row-attributes)
             [:div (forms/form-block-attributes {:ratio 50})
                   [vehicle-number-of-seats-field]]
             [:div (forms/form-block-attributes {:ratio 50})
                   [vehicle-number-of-bunks-field]]]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-thumbnail-picker
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [storage/media-picker ::vehicle-thumbnail-picker
                             {:disabled?    editor-disabled?
                              :indent       {:top :l :vertical :xs}
                              :label        :thumbnail
                              :toggle-label :select-image!
                              :thumbnails   {:height :2xl :width :4xl}
                              :value-path   [:vehicles :vehicle-editor/edited-item :thumbnail]}]))

(defn- vehicle-images-picker
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [storage/media-picker ::vehicle-images-picker
                             {:disabled?    editor-disabled?
                              :indent       {:top :l :vertical :xs}
                              :label        :images
                              :multiple?    true
                              :toggle-label :select-images!
                              :thumbnails   {:height :2xl :width :4xl}
                              :value-path   [:vehicles :vehicle-editor/edited-item :images]}]))

(defn- vehicle-images
  []
  [:<> [vehicle-thumbnail-picker]
       [vehicle-images-picker]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-editor-ghost-view :vehicles.vehicle-editor
                                 {:padding "0 12px"}])

(defn- menu-bar
  []
  [common/item-editor-menu-bar :vehicles.vehicle-editor
                               {:menu-items [{:label   :data
                                              :view-id :data
                                              :change-keys [:name :type :construction-year :number-of-bunks :number-of-seats]}
                                             {:label   :images
                                              :view-id :images
                                              :change-keys [:images :thumbnail]}]}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :vehicles.vehicle-editor])]
       [:<> [menu-bar]
            (case current-view-id :data   [vehicle-data]
                                  :images [vehicle-images])]))

(defn- vehicle-editor
  []
  [item-editor/body :vehicles.vehicle-editor
                    {:auto-title?      true
                     :form-element     #'view-selector
                     :ghost-element    #'ghost-view
                     :item-path        [:vehicles :vehicle-editor/edited-item]
                     :label-key        :name
                     :suggestion-keys  [:name]
                     :suggestions-path [:vehicles :vehicle-editor/suggestions]}])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- label-bar
  []
  (let [vehicle-name @(a/subscribe [:db/get-item [:vehicles :vehicle-editor/edited-item :name]])]
       [common/item-editor-label-bar :vehicles.vehicle-editor
                                     {:label       vehicle-name
                                      :placeholder :unnamed-vehicle}]))

(defn- breadcrumbs
  []
  (let [vehicle-name @(a/subscribe [:db/get-item [:vehicles :vehicle-editor/edited-item :name]])]
       [common/item-editor-breadcrumbs :vehicles.vehicle-editor
                                       {:crumbs [{:label :app-home
                                                  :route "/@app-home"}
                                                 {:label :vehicles
                                                  :route "/@app-home/vehicles"}
                                                 {:label vehicle-name
                                                  :placeholder :unnamed-vehicle}]}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [vehicle-editor]
       [elements/horizontal-separator {:size :xxl}]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
