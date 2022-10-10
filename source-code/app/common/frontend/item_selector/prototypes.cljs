
(ns app.common.frontend.item-selector.prototypes
    (:require [mid-fruits.candy :refer [param return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn selector-props-prototype
  ; @param (map) selector-props
  ;
  ; @return (map)
  ;  {:export-item-f (function)
  ;   :import-count-f (function)
  ;   :import-id-f (function)}
  [selector-props]
  (merge {:export-item-f  (fn [item-id item-count] item-id)
          :import-count-f (fn [_] 1)
          :import-id-f    return}
         (param selector-props)))
