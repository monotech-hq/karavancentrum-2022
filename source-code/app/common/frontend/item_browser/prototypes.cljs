
(ns app.common.frontend.item-browser.prototypes
    (:require [candy.api :refer [param]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn browser-props-prototype
  ; @param (map) browser-props
  ;
  ; @return (map)
  [browser-props]
  (merge {}
         (param browser-props)))
