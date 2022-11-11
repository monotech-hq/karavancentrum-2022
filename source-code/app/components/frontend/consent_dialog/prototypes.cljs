
(ns app.components.frontend.consent-dialog.prototypes
    (:require [mid-fruits.candy :refer [param]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dialog-props-prototype
  ; @param (map) dialog-props
  ;  {}
  ;
  ; @return (map)
  ;  {}
  [dialog-props]
  (merge {}
         (param dialog-props)))
