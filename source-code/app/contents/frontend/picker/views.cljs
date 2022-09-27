
(ns app.contents.frontend.picker.views
    (:require [app.contents.frontend.picker.prototypes :as picker.prototypes]
              [app-fruits.html                         :as html]
              [mid-fruits.random                       :as random]
              [reagent.api                             :as reagent]
              [x.app-core.api                          :as a]
              [x.app-elements.api                      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-picker-preview-content
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:max-lines (integer)(opt)}
  [picker-id {:keys [max-lines] :as picker-props}]
  (let [content-body @(a/subscribe [:contents.content-picker/get-content-body picker-id])]
       [elements/text {:color       :muted
                       :content     (html/to-hiccup content-body)
                       :font-size   :xs
                       :indent      {:horizontal :xxs :vertical :xs}
                       :max-lines   max-lines
                       :placeholder :downloading...}]))

(defn- content-picker-preview-card
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)}
  [picker-id {:keys [disabled?] :as picker-props}]
  [:div {:style {:display :flex}}
        [elements/card {:content          [content-picker-preview-content picker-id picker-props]
                        :background-color :highlight
                        :border-radius    :s
                        :disabled?        disabled?
                        :horizontal-align :left
                        :min-width        :xxs}]])

(defn- content-picker-preview-body
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ; @param (keyword) picked-content
  [picker-id picker-props picked-content]
  (reagent/lifecycles {:component-did-mount  (fn [] (a/dispatch [:contents.content-picker/request-content! picker-id picker-props]))
                       :component-did-update (fn [] (a/dispatch [:contents.content-picker/request-content! picker-id picker-props]))
                       :reagent-render       (fn [_ picker-props] [content-picker-preview-card picker-id picker-props])}))

(defn- content-picker-preview
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:value-path (vector)}
  [picker-id {:keys [value-path] :as picker-props}]
  (if-let [picked-content @(a/subscribe [:db/get-item value-path])]
          [content-picker-preview-body picker-id picker-props picked-content]))

(defn- content-picker-button
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;   :value-path (vector)}
  [picker-id {:keys [disabled? value-path]}]
  (let [on-click [:contents.content-selector/load-selector! :contents.content-selector {:value-path value-path}]]
       [:div {:style {:display :flex}}
             [elements/button {:color     :muted
                               :disabled? disabled?
                               :font-size :xs
                               :label     :select-content!
                               :on-click  on-click}]]))

(defn- content-picker-label
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :required? (boolean)(opt)}
  [_ {:keys [disabled? info-text label required?]}]
  (if label [elements/label {:content   label
                             :disabled? disabled?
                             :info-text info-text
                             :required? required?}]))

(defn- content-picker-body
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  [picker-id picker-props]
  [:<> [content-picker-label   picker-id picker-props]
       [content-picker-button  picker-id picker-props]
       [content-picker-preview picker-id picker-props]])

(defn- content-picker
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {}
  [picker-id {:keys [indent] :as picker-props}]
  [elements/blank picker-id
                  {:content [content-picker-body picker-id picker-props]
                   :indent  indent}])

(defn element
  ; @param (keyword)(opt) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)
  ;    Default: false
  ;   :indent (map)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :max-lines (integer)(opt)
  ;   :required? (boolean)(opt)
  ;    Default: false
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [contents/content-picker {...}]
  ;
  ; @usage
  ;  [contents/content-picker :my-picker {...}]
  ([picker-props]
   [element (random/generate-keyword) picker-props])

  ([picker-id picker-props]
   (let [picker-props (picker.prototypes/picker-props-prototype picker-props)]
        [content-picker picker-id picker-props])))
