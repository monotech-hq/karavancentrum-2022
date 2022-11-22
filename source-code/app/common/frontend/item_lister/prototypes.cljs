
(ns app.common.frontend.item-lister.prototypes
    (:require [candy.api :refer [param]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn lister-props-prototype
  ; @param (map) lister-props
  ;
  ; @return (map)
  [lister-props]
  (merge {}
         (param lister-props)))
