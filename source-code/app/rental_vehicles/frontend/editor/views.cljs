
(ns app.rental-vehicles.frontend.editor.views
    (:require [app.common.frontend.api   :as common]
              [app.contents.frontend.api :as contents]
              [app.storage.frontend.api  :as storage]
              [forms.api                 :as forms]
              [layouts.surface-a.api     :as surface-a]
              [mid-fruits.time           :as time]
              [plugins.item-editor.api   :as item-editor]
              [x.app-core.api            :as a]
              [x.app-elements.api        :as elements]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-public-link
  []
  (let [editor-disabled?    @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])
        vehicle-public-link @(a/subscribe [:rental-vehicles.editor/get-vehicle-public-link])]
       [common/data-element ::vehicle-public-link
                            {:disabled? editor-disabled?
                             :indent    {:top :m :vertical :s}
                             :label     :public-link
                             :value     vehicle-public-link}]))

(defn- vehicle-visibility-radio-button
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [elements/radio-button ::vehicle-visibility-radio-button
                              {:disabled?       editor-disabled?
                               :indent          {:top :m :vertical :s}
                               :label           :visibility-on-the-website
                               :options         [{:label :public-content  :helper :visible-to-everyone     :value :public}
                                                 {:label :private-content :helper :only-visible-to-editors :value :private}]
                               :option-helper-f :helper
                               :option-label-f  :label
                               :option-value-f  :value
                               :value-path      [:rental-vehicles :editor/edited-item :visibility]}]))

(defn- vehicle-settings
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [common/surface-box ::vehicle-settings
                           {:content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 100})
                                                      [vehicle-visibility-radio-button]]]
                                          [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 100})
                                                      [vehicle-public-link]]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :settings}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-description-picker
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [contents/content-picker ::vehicle-description-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:vertical :s}
                                 :value-path [:rental-vehicles :editor/edited-item :description]}]))

(defn- vehicle-content
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [common/surface-box ::vehicle-content
                           {:content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 100})
                                                      [vehicle-description-picker]]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :description}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-construction-year-field
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])
        current-year      (time/get-year)]
       [elements/text-field ::vehicle-construction-year-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :construction-year
                             :placeholder current-year
                             :value-path  [:rental-vehicles :editor/edited-item :construction-year]}]))

(defn- vehicle-number-of-seats-counter
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [elements/counter ::vehicle-number-of-seats-counter
                            {:disabled?  editor-disabled?
                             :indent     {:top :m :vertical :s}
                             :label      :number-of-seats
                             :min-value  0
                             :max-value  10
                             :value-path [:rental-vehicles :editor/edited-item :number-of-seats]}]))

(defn- vehicle-number-of-bunks-counter
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [elements/counter ::vehicle-number-of-bunks-counter
                            {:disabled?  editor-disabled?
                             :indent     {:top :m :vertical :s}
                             :label      :number-of-bunks
                             :min-value  0
                             :max-value  10
                             :value-path [:rental-vehicles :editor/edited-item :number-of-bunks]}]))

(defn- vehicle-technical-data
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [common/surface-box ::vehicle-technical-data
                           {:indent  {:top :m}
                            :content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 40})
                                                      [vehicle-construction-year-field]]
                                                [:div (forms/form-block-attributes {:ratio 10})]
                                                [:div (forms/form-block-attributes {:ratio 25})
                                                      [vehicle-number-of-seats-counter]]
                                                [:div (forms/form-block-attributes {:ratio 25})
                                                      [vehicle-number-of-bunks-counter]]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :technical-data}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-image-picker
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [storage/media-picker ::vehicle-image-picker
                             {:autosave?     false
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:vertical :s}
                              :multi-select? true
                              :toggle-label  :select-images!
                              :thumbnail     {:height :3xl :width :5xl}
                              :value-path    [:rental-vehicles :editor/edited-item :images]}]))

(defn- vehicle-images
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [common/surface-box ::vehicle-images
                           {:content [:<> [vehicle-image-picker]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :images}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-thumbnail-picker
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [storage/media-picker ::vehicle-thumbnail-picker
                             {:autosave?     true
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:vertical :s}
                              :multi-select? false
                              :toggle-label  :select-image!
                              :thumbnail     {:height :3xl :width :5xl}
                              :value-path    [:rental-vehicles :editor/edited-item :thumbnail]}]))

(defn- vehicle-thumbnail
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [common/surface-box ::vehicle-thumbnail
                           {:content [:<> [vehicle-thumbnail-picker]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :thumbnail}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-name-field
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [elements/combo-box ::vehicle-name-field
                           {:autofocus?   true
                            :disabled?    editor-disabled?
                            :indent       {:top :m :vertical :s}
                            :label        :name
                            :options-path [:rental-vehicles :editor/suggestions :name]
                            :placeholder  :vehicle-name
                            :value-path   [:rental-vehicles :editor/edited-item :name]}]))

(defn- vehicle-type-select
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [elements/select ::vehicle-type-select
                        {:disabled?     editor-disabled?
                         :indent        {:top :m :vertical :s}
                         :label         :type
                         :layout        :select
                         :options-label :vehicle-type
                         :options       [:alcove-rv :semi-integrated-rv :van-rv :caravan :trailer]
                         :value-path    [:rental-vehicles :editor/edited-item :type]}]))

(defn- vehicle-basic-data
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [common/surface-box ::vehicle-basic-data
                           {:content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 60})
                                                      [vehicle-name-field]]
                                                [:div (forms/form-block-attributes {:ratio 40})
                                                      [vehicle-type-select]]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :basic-data}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- vehicle-data
  []
  [:<> [vehicle-basic-data]
       [vehicle-technical-data]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [common/item-editor-menu-bar :rental-vehicles.editor
                                    {:disabled?  editor-disabled?
                                     :menu-items [{:label :data      :change-keys [:name :type :construction-year
                                                                                   :number-of-bunks :number-of-seats]}
                                                  {:label :thumbnail :change-keys [:thumbnail]}
                                                  {:label :images    :change-keys [:images]}
                                                  {:label :content   :change-keys [:description]}
                                                  {:label :settings  :change-keys [:visibility]}]}]))

(defn- body
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :rental-vehicles.editor])]
       (case current-view-id :data      [vehicle-data]
                             :content   [vehicle-content]
                             :thumbnail [vehicle-thumbnail]
                             :images    [vehicle-images]
                             :settings  [vehicle-settings])))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- controls
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])]
       [common/item-editor-controls :rental-vehicles.editor
                                    {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])
        vehicle-name     @(a/subscribe [:db/get-item [:rental-vehicles :editor/edited-item :name]])]
       [common/surface-breadcrumbs :rental-vehicles.editor/view
                                   {:crumbs [{:label :app-home    :route "/@app-home"}
                                             {:label :rental-vehicles    :route "/@app-home/rental-vehicles"}
                                             {:label vehicle-name :placeholder :unnamed-vehicle}]
                                    :disabled? editor-disabled?}]))

(defn- label
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :rental-vehicles.editor])
        vehicle-name     @(a/subscribe [:db/get-item [:rental-vehicles :editor/edited-item :name]])]
       [common/surface-label :rental-vehicles.editor/view
                             {:disabled?   editor-disabled?
                              :label       vehicle-name
                              :placeholder :unnamed-vehicle}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn- header
  []
  [:<> [:div {:style {:display "flex" :justify-content "space-between" :flex-wrap "wrap" :grid-row-gap "48px"}}
             [:div [label]
                   [breadcrumbs]]
             [:div [controls]]]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]])

(defn- view-structure
  []
  [:<> [header]
       [body]])

(defn- vehicle-editor
  []
  (let [current-year (time/get-year)]
       [item-editor/body :rental-vehicles.editor
                         {:auto-title?      true
                          :form-element     #'view-structure
                          :error-element    [common/error-content {:error :the-item-you-opened-may-be-broken}]
                          :ghost-element    #'common/item-editor-ghost-element
                          :initial-item     {:construction-year current-year
                                             :number-of-bunks   0
                                             :number-of-seats   0
                                             :type              :alcove-rv
                                             :visibility        :public}
                          :item-path        [:rental-vehicles :editor/edited-item]
                          :label-key        :name
                          :suggestion-keys  [:name]
                          :suggestions-path [:rental-vehicles :editor/suggestions]}]))

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'vehicle-editor}])
