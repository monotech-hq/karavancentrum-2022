
(ns app.contents.frontend.picker.views
    (:require [app.contents.frontend.picker.prototypes :as picker.prototypes]
              [app.contents.frontend.preview.views     :as preview.views]
              [mid-fruits.random                       :as random]
              [x.app-elements.api                      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-picker-preview
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  [picker-id picker-props]
  (let [preview-props (picker.prototypes/preview-props-prototype picker-id picker-props)]
       [preview.views/element picker-id preview-props]))

(defn- content-picker-button
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:disabled? (boolean)(opt)}
  [picker-id {:keys [disabled?] :as picker-props}]
  (let [selector-props (picker.prototypes/selector-props-prototype picker-id picker-props)
        on-click [:contents.selector/load-selector! :contents.selector selector-props]]
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
  ;  {:indent (map)(opt)}
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
  ;   :placeholder (metamorphic-content)(opt)
  ;   :on-change (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja a kiválasztott elemet.
  ;   :on-save (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja a kiválasztott elemet.
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
   (let [picker-props (picker.prototypes/picker-props-prototype picker-id picker-props)]
        [content-picker picker-id picker-props])))
