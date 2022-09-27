
(ns app.common.frontend.item-selector.prototypes
    (:require [mid-fruits.candy :refer [param return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn selector-props-prototype
  ; @param (map) selector-props
  ;
  ; @return (map)
  ;  {:export-id-f (function)
  ;   :import-id-f (function)}
  [selector-props]
  (merge {:export-id-f return
          :import-id-f return}
         (param selector-props)))
