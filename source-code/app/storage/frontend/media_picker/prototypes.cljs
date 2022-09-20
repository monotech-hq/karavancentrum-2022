
(ns app.storage.frontend.media-picker.prototypes
    (:require [mid-fruits.candy :refer [param]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn picker-props-prototype
  ; @param (map) picker-props
  ;  {:thumbnails (map)(opt)}
  ;
  ; @return (map)
  ;  {:thumbnails (map)}
  [{:keys [thumbnails] :as picker-props}]
  (merge {}
         (param picker-props)
         (if thumbnails (merge {:max-count 8
                                :height    :4xl
                                :width     :4xl}
                               (param thumbnails)))))

  
