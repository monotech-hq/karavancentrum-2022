
(ns app.common.frontend.surface-button.views
    (:require [app.common.frontend.surface-button.prototypes :as surface-button.prototypes]
              [mid-fruits.random                             :as random]
              [x.app-elements.api                            :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn element
  ; @param (keyword)(opt) button-id
  ; @param (map) button-props
  ;  {}
  ;
  ; @usage
  ;  [surface-button {...}]
  ;
  ; @usage
  ;  [surface-button :my-element {...}]
  ([button-props]
   [element (random/generate-keyword) button-props])

  ([button-id button-props]
   (let [button-props (surface-button.prototypes/button-props-prototype button-props)]
        [elements/button button-id button-props])))
