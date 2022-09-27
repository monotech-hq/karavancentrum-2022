
(ns app.vehicles.frontend.editor.views
    (:require [app.common.frontend.api   :as common]
              [app.contents.frontend.api :as contents]
              [app.storage.frontend.api  :as storage]
              [forms.api                 :as forms]
              [layouts.surface-a.api     :as surface-a]
              [mid-fruits.normalize      :as normalize]
              [plugins.item-editor.api   :as item-editor]
              [x.app-core.api            :as a]
              [x.app-elements.api        :as elements]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-public-link-label
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/label ::vehicle-public-link-label
                       {:content   :public-link
                        :disabled? editor-disabled?
                        :indent    {:top :l :vertical :xs}}]))

(defn- vehicle-public-link-value
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])
        vehicle-name     @(a/subscribe [:db/get-item [:vehicles :vehicle-editor/edited-item :name]])
        normalized-name   (normalize/clean-url vehicle-name)
        public-link       (str "/berelheto-jarmuveink/"normalized-name)]
       [elements/label ::vehicle-public-link-value
                       {:color     :highlight
                        :content   public-link
                        :disabled? editor-disabled?
                        :indent    {:vertical :xs}}]))

(defn- vehicle-public-link
  []
  [:<> [vehicle-public-link-label]
       [vehicle-public-link-value]])

(defn- vehicle-visibility-radio-button
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/radio-button ::vehicle-visibility-radio-button
                              {:disabled?       editor-disabled?
                               :indent          {:top :l :vertical :xs}
                               :label           :visibility-on-the-website
                               :options         [{:label :public-content  :helper :visible-to-everyone     :value :public}
                                                 {:label :private-content :helper :only-visible-to-editors :value :private}]
                               :option-helper-f :helper
                               :option-label-f  :label
                               :option-value-f  :value
                               :value-path      [:vehicles :vehicle-editor/edited-item :visibility]}]))

(defn- vehicle-settings
  []
  [:<> [:div (forms/form-row-attributes)
             [:div (forms/form-block-attributes {:ratio 25})
                   [vehicle-visibility-radio-button]]]
       [:div (forms/form-row-attributes)
             [:div (forms/form-block-attributes {:ratio 25})
                   [vehicle-public-link]]]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-description-picker
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [contents/content-picker ::vehicle-description-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:top :l :vertical :xs}
                                 :label      :description
                                 :value-path [:vehicles :vehicle-editor/edited-item :description]}]))

(defn- vehicle-description
  []
  [:<> [:div (forms/form-row-attributes)
             [:div (forms/form-block-attributes {:ratio 100})
                   [vehicle-description-picker]]]])

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

(defn- vehicle-number-of-seats-counter
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/counter ::vehicle-number-of-seats-counter
                            {:disabled?  editor-disabled?
                             :indent     {:top :l :vertical :xs}
                             :label      :number-of-seats
                             :min-value  0
                             :max-value  10
                             :value-path [:vehicles :vehicle-editor/edited-item :number-of-seats]}]))

(defn- vehicle-number-of-bunks-counter
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/counter ::vehicle-number-of-bunks-counter
                            {:disabled?  editor-disabled?
                             :indent     {:top :l :vertical :xs}
                             :label      :number-of-bunks
                             :min-value  0
                             :max-value  10
                             :value-path [:vehicles :vehicle-editor/edited-item :number-of-bunks]}]))

(defn- vehicle-type-select
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [elements/select ::vehicle-type-select
                        {:disabled?     editor-disabled?
                         :indent        {:top :l :vertical :xs}
                         :label         :type
                         :layout        :select
                         :options-label :vehicle-type
                         :options       [:alcove-rv :semi-integrated-rv :van-rv :caravan :trailer]
                         :value-path    [:vehicles :vehicle-editor/edited-item :type]}]))

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
             [:div (forms/form-block-attributes {:ratio 25})
                   [vehicle-number-of-seats-counter]]
             [:div (forms/form-block-attributes {:ratio 25})
                   [vehicle-number-of-bunks-counter]]
             [:div (forms/form-block-attributes {:ratio 50})]]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-thumbnail-picker
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [storage/media-picker ::vehicle-thumbnail-picker
                             {:autosave?     true
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:top :l :vertical :xs}
                              :label         :thumbnail
                              :multi-select? false
                              :toggle-label  :select-image!
                              :thumbnails    {:height :2xl :width :4xl}
                              :value-path    [:vehicles :vehicle-editor/edited-item :thumbnail]}]))

(defn- vehicle-images-picker
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [storage/media-picker ::vehicle-images-picker
                             {:autosave?     false
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:top :l :vertical :xs}
                              :label         :images
                              :multi-select? true
                              :toggle-label  :select-images!
                              :thumbnails    {:height :2xl :width :4xl}
                              :value-path    [:vehicles :vehicle-editor/edited-item :images]}]))

(defn- vehicle-images
  []
  [:<> [vehicle-thumbnail-picker]
       [vehicle-images-picker]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [common/item-editor-menu-bar :vehicles.vehicle-editor
                                    {:disabled?  editor-disabled?
                                     :menu-items [{:label :data        :change-keys [:name :type :construction-year
                                                                                     :number-of-bunks :number-of-seats]}
                                                  {:label :description :change-keys [:description]}
                                                  {:label :images      :change-keys [:images :thumbnail]}
                                                  {:label :settings    :change-keys [:visibility]}]}]))

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :vehicles.vehicle-editor])]
       (case current-view-id :data        [vehicle-data]
                             :description [vehicle-description]
                             :images      [vehicle-images]
                             :settings    [vehicle-settings])))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])]
       [common/item-editor-control-bar :vehicles.vehicle-editor
                                       {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])
        vehicle-name     @(a/subscribe [:db/get-item [:vehicles :vehicle-editor/edited-item :name]])]
       [common/surface-breadcrumbs :vehicles.vehicle-editor/view
                                   {:crumbs [{:label :app-home    :route "/@app-home"}
                                             {:label :vehicles    :route "/@app-home/vehicles"}
                                             {:label vehicle-name :placeholder :unnamed-vehicle}]
                                    :disabled? editor-disabled?}]))

(defn- label-bar
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.vehicle-editor])
        vehicle-name     @(a/subscribe [:db/get-item [:vehicles :vehicle-editor/edited-item :name]])]
       [common/surface-label :vehicles.vehicle-editor/view
                             {:disabled?   editor-disabled?
                              :label       vehicle-name
                              :placeholder :unnamed-vehicle}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- header
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]])

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [header]
        [view-selector]
        [elements/horizontal-separator {:size :xxl}]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [control-bar]]])

(defn- vehicle-editor
  []
  [item-editor/body :vehicles.vehicle-editor
                    {:auto-title?      true
                     :form-element     #'view-structure
                     :ghost-element    #'common/item-editor-ghost-view
                     :initial-item     {:number-of-bunks 0
                                        :number-of-seats 0
                                        :type       :alcove-rv
                                        :visibility :public}
                     :item-path        [:vehicles :vehicle-editor/edited-item]
                     :label-key        :name
                     :suggestion-keys  [:name]
                     :suggestions-path [:vehicles :vehicle-editor/suggestions]}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'vehicle-editor}])
