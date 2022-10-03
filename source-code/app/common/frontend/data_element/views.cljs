
(ns app.common.frontend.data-element.views
    (:require [app.common.frontend.data-element.prototypes :as data-element.prototypes]
              [mid-fruits.random                           :as random]
              [x.app-elements.api                          :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- data-element-label
  ; @param (keyword) element-id
  ; @param (map) element-props
  ;  {:disabled? (boolean)(opt)
  ;   :font-size (keyword)
  ;   :label (metamorphic-content)}
  [_ {:keys [disabled? font-size label]}]
  [elements/label {:content             label
                   :disabled?           disabled?
                   :font-size           font-size
                   :horizontal-position :left
                   :selectable?         false}])

(defn- data-element-value
  ; @param (keyword) element-id
  ; @param (map) element-props
  ;  {:disabled? (boolean)(opt)
  ;   :font-size (keyword)
  ;   :placeholder (metamorphic-content)(opt)
  ;   :value (metamorphic-content)(opt)}
  [_ {:keys [disabled? font-size placeholder value]}]
  [elements/label {:color               :muted
                   :content             value
                   :disabled?           disabled?
                   :font-size           font-size
                   :horizontal-position :left
                   :min-width           :xxs
                   :placeholder         placeholder
                   :selectable?         true}])

(defn- data-element
  ; @param (keyword) element-id
  ; @param (map) element-props
  ;  {:indent (map)(opt)}
  [element-id {:keys [indent] :as element-props}]
  [elements/blank {:indent  indent
                   :content [:<> [data-element-label element-id element-props]
                                 [data-element-value element-id element-props]]}])

(defn element
  ; @param (keyword)(opt) element-id
  ; @param (map) element-props
  ;  {:disabled? (boolean)(opt)}
  ;    Default: false
  ;   :font-size (keyword)(opt)
  ;    :xxs, :xs, :s, :m, :l, :xl, :xxl
  ;    Default: :s
  ;   :indent (map)(opt)
  ;   :label (metamorphic-content)
  ;   :placeholder (metamorphic-content)(opt)
  ;   :value (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/data-element {...}]
  ;
  ; @usage
  ;  [common/data-element :my-element {...}]
  ([element-props]
   [element (random/generate-keyword) element-props])

  ([element-id element-props]
   (let [element-props (data-element.prototypes/element-props-prototype element-props)]
        [data-element element-id element-props])))
