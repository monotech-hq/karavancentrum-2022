
(ns app.contents.frontend.picker.prototypes
    (:require [mid-fruits.candy :refer [param]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn picker-props-prototype
  ; @param (map) picker-props
  ;
  ; @return (map)
  ;  {}
  [picker-props]
  (merge {}
         (param picker-props)))
