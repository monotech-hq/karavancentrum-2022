
(ns app.contents.frontend.preview.prototypes
    (:require [mid-fruits.candy :refer [param]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn preview-props-prototype
  ; @param (keyword) preview-id
  ; @param (map) preview-props
  ;
  ; @return (map)
  ;  {:color (keyword)
  ;   :font-size (keyword)
  ;   :font-weight (keyword)}
  [_ preview-props]
  (merge {:color       :muted
          :font-size   :s
          :font-weight :normal}
         (param preview-props)))
