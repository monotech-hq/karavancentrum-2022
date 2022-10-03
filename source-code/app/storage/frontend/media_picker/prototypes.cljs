
(ns app.storage.frontend.media-picker.prototypes
    (:require [mid-fruits.candy :refer [param]]
              [re-frame.api     :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn preview-props-prototype
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {}
  ;
  ; @return (map)
  ;  {}
  [picker-id {:keys [disabled? no-media-label] :as picker-props}]
  (let [picked-items @(r/subscribe [:storage.media-picker/get-picked-items picker-id picker-props])]
       {:disabled?     disabled?
        :media         picked-items
        :indent        {:top :m}
        :no-media-label no-media-label}))

(defn picker-props-prototype
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;
  ; @return (map)
  [picker-id picker-props]
  (merge {}
         (param picker-props)))
