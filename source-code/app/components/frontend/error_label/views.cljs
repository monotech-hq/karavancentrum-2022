
(ns app.components.frontend.error-label.views
    (:require [app.components.frontend.error-label.prototypes :as error-label.prototypes]
              [elements.api                                   :as elements]
              [random.api                                     :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- error-message-label
  ; @param (keyword) element-id
  ; @param (map) element-props
  [_ {:keys [error]}]
  [elements/label {:color       :warning
                   :content     error
                   :font-size   :xs
                   :line-height :block}])

(defn- error-element
  ; @param (keyword) element-id
  ; @param (map) element-props
  [element-id element-props]
  [error-message-label element-id element-props])

(defn component
  ; @param (keyword)(opt) element-id
  ; @param (map) element-props
  ;  {:error (metamorphic-content)}
  ;
  ; @usage
  ;  [error-element {...}]
  ;
  ; @usage
  ;  [error-element :my-error-element {...}]
  ([element-props]
   [component (random/generate-keyword) element-props])

  ([element-id element-props]
   (let [];element-props (error-label.prototypes/element-props-prototype element-props)
        [error-element element-id element-props])))
