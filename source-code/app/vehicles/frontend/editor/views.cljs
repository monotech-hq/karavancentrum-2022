
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

(defn- vehicle-thumbnail-label
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/label ::vehicle-thumbnail-label
                       {:content   :thumbnail
                        :disabled? editor-disabled?
                        :indent    {:top :l :vertical :xs}}]))

(defn- vehicle-thumbnail
  []
  (let [editor-disabled?  @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])
        vehicle-thumbnail @(a/subscribe [:db/get-item [:vehicles :vehicle-editor/edited-item :thumbnail]])]
       [elements/thumbnail ::vehicle-thumbnail
                           {:border-radius :s
                            :disabled?     editor-disabled?
                            :height        :l
                            :indent        {:top :xxs :vertical :xs}
                            :uri           vehicle-thumbnail
                            :width         :xxl}]))

(defn- vehicle-thumbnail-button
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])
        on-click [:storage.media-selector/load-selector! :parts.part-editor/thumbnail-selector
                                                         {:value-path [:vehicles :vehicle-editor/edited-item :thumbnail]}]]
       [elements/button ::vehicle-thumbnail-button
                        {:color     :muted
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:vertical :xs}
                         :label     :select-image!
                         :on-click  on-click}]))

(defn- vehicle-thumbnail-selector
  []
  [:<> [vehicle-thumbnail-label]
       [:div (forms/form-row-attributes)
             [vehicle-thumbnail-button]]
       [vehicle-thumbnail]])

(defn- vehicle-images-label
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/label ::vehicle-images-label
                       {:content   :images
                        :disabled? editor-disabled?
                        :indent    {:top :l :vertical :xs}}]))

(defn- vehicle-images-button
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])
        on-click [:storage.media-selector/load-selector! :vehicles.vehicle-editor/thumbnail-selector
                                                         {:multiple?  true
                                                          :value-path [:vehicles :vehicle-editor/edited-item :images]}]]
       [elements/button ::vehicle-images-button
                        {:color     :muted
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:vertical :xs}
                         :label     :select-images!
                         :on-click  on-click}]))

(defn- vehicle-image-list
  []
  (let [vehicle-images @(a/subscribe [:db/get-item [:vehicles :vehicle-editor/edited-item :images]])]
       [common/item-editor-image-list :vehicles.vehicle-editor
                                      {:images vehicle-images}]))

(defn- vehicle-image-selector
  []
  [:<> [vehicle-images-label]
       [:div (forms/form-row-attributes)
             [vehicle-images-button]]
       [vehicle-image-list]])

(defn- vehicle-images
  []
  [:<> [vehicle-thumbnail-selector]
       [vehicle-image-selector]])

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
