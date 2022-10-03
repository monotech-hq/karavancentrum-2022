
(ns app.common.frontend.item-editor.views
    (:require [app.common.frontend.surface.views        :as surface.views]
              [app.common.frontend.surface-button.views :as surface-button.views]
              [mid-fruits.vector                        :as vector]
              [x.app-components.api                     :as components]
              [x.app-core.api                           :as a]
              [x.app-elements.api                       :as elements]))

;; -- Color-picker component --------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-color-picker-label
  ; @param (keyword) editor-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;   :label (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-editor-color-picker-label :my-editor {...}]
  [_ {:keys [disabled? label]}]
  (if label [elements/label {:content   :color
                             :disabled? disabled?}]))

(defn item-editor-color-picker-button
  ; @param (keyword) editor-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [common/item-editor-color-picker-button :my-editor {...}]
  [editor-id {:keys [disabled? value-path]}]
  [elements/button {:color            :muted
                    :disabled?        disabled?
                    :font-size        :xs
                    :horizontal-align :left
                    :label            :choose-color!
                    :on-click         [:elements.color-selector/render-selector! editor-id {:value-path value-path}]}])

(defn item-editor-color-picker-value
  ; @param (keyword) editor-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [common/item-editor-color-picker-value :my-editor {...}]
  [_ {:keys [disabled? value-path]}]
  (let [picked-colors @(a/subscribe [:db/get-item value-path])]
       [elements/color-stamp {:colors    picked-colors
                              :disabled? disabled?
                              :size      :xxl}]))

(defn item-editor-color-picker
  ; @param (keyword) editor-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;   :indent (map)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [common/item-editor-color-picker :my-editor {...}]
  [editor-id {:keys [indent] :as picker-props}]
  [elements/blank {:indent indent
                   :content [:<> [item-editor-color-picker-label editor-id picker-props]
                                 [:div {:style {:display :flex}}
                                       [item-editor-color-picker-button editor-id picker-props]]
                                 [item-editor-color-picker-value editor-id picker-props]]}])

;; -- Menu-bar components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-menu-item-props
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ; @param (map) item-props
  ;  {:change-keys (keywords in vector)(opt)
  ;   :label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-editor-menu-item-props :my-editor {...} {...}]
  ;
  ; @return (map)
  ;  {:active? (boolean)
  ;   :badge-color (keyword)
  ;   :label (metamorphic-content)
  ;   :on-click (metamorphic-event)}
  [editor-id _ {:keys [change-keys label]}]
  (let [current-view @(a/subscribe [:gestures/get-current-view-id editor-id])
        changed? (if change-keys @(a/subscribe [:item-editor/form-changed? editor-id change-keys]))]
       {:active?     (= label current-view)
        :badge-color (if changed? :primary)
        :label       label
        :on-click    [:gestures/change-view! editor-id label]}))

(defn item-editor-menu-bar
  ; A komponens használatához ne felejts el inicializálni egy gestures/view-handler
  ; kezelőt, az editor-id azonosítóval!
  ;
  ; @param (keyword) editor-id
  ; @param (map) bar-props
  ;  {:disabled? (boolean)(opt)
  ;   :menu-items (maps in vector)
  ;    [{:change-keys (keywords in vector)(opt)
  ;      :label (metamorphic-content)}]}
  ;
  ; @usage
  ;  [common/item-editor-menu-bar :my-editor {...}]
  [editor-id {:keys [disabled? menu-items] :as bar-props}]
  (letfn [(f [menu-items menu-item] (conj menu-items (item-editor-menu-item-props editor-id bar-props menu-item)))]
         [:<> [elements/menu-bar ::item-editor-menu-bar {:disabled?  disabled?
                                                         :menu-items (reduce f [] menu-items)}]]))
              ;[elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]]))

;; -- Ghost-view components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-editor-ghost-element
  ; @param (keyword) editor-id
  ; @param (map) element-props
  ;
  ; @usage
  ;  [common/item-editor-ghost-element :my-editor {...}]
  [editor-id _]
  [surface.views/surface-box-layout-ghost-view editor-id {:breadcrumb-count 4}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn revert-item-button
  ; @param (keyword) editor-id
  ; @param (map) element-props
  ;
  ; @usage
  ;  [common/revert-item-button :my-editor {...}]
  [editor-id {:keys [disabled?]}]
  (let [item-changed? @(a/subscribe [:item-editor/item-changed? editor-id])]
       [surface-button.views/element ::revert-item-button
                                     {:disabled?   (or disabled? (not item-changed?))
                                      :hover-color :highlight
                                      :icon        :settings_backup_restore
                                      :label       :revert!
                                      :on-click    [:item-editor/revert-item! editor-id]}]))

(defn save-item-button
  ; @param (keyword) editor-id
  ; @param (map) element-props
  ;
  ; @usage
  ;  [common/save-item-button :my-editor {...}]
  [editor-id {:keys [disabled?]}]
  [surface-button.views/element ::save-item-button
                                {:background-color "#5a4aff"
                                 :color            "white"
                                 :disabled?        disabled?
                                 :icon             :save
                                 :label            :save!
                                 :on-click         [:item-editor/save-item! editor-id]}])

(defn item-editor-controls
  ; @param (keyword) editor-id
  ; @param (map) element-props
  ;
  ; @usage
  ;  [common/item-editor-controls :my-editor {...}]
  [editor-id element-props]
  [:div {:style {:display "flex" :grid-column-gap "12px"}}
        [:<> [revert-item-button editor-id element-props]
             [save-item-button   editor-id element-props]]])
