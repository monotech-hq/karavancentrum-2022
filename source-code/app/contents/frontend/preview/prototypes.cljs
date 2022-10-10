
(ns app.contents.frontend.preview.prototypes
    (:require [mid-fruits.candy :refer [param]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn preview-props-prototype
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;
  ; @return (map)
  ;  {}
  [_ preview-props]
  (merge {:color :muted}
         (param preview-props)))
