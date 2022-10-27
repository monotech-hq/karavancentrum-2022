

(ns site.karavancentrum.components.grid
    (:require [site.karavancentrum.components.link :as link]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn grid
  [& args]
  (if (-> args first map?)
      [:div.kc-grid (first args) args]
      [:div.kc-grid args]))

(defn view
  [args]
  [grid args])
