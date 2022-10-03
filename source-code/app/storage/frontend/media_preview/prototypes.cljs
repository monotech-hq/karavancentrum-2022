
(ns app.storage.frontend.media-preview.prototypes
    (:require [mid-fruits.candy :refer [param]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn preview-props-prototype
  ; @param (map) preview-props
  ;  {:media (string or strings in vector)
  ;   :thumbnail (map)(opt)}
  ;
  ; @return (map)
  ;  {:media (strings in vector)
  ;   :thumbnail (map)}
  [{:keys [media thumbnail] :as preview-props}]
  (merge {}
         (param preview-props)
         (if (string? media)
             {:media [media]})
         {:thumbnail (merge {:max-count 8
                             :height    :5xl
                             :width     :5xl}
                            (param thumbnail))}))
