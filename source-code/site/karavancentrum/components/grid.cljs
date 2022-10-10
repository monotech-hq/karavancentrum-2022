

(ns site.karavancentrum.components.grid)

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn grid [& args]
  (if (-> args first map?)
    [:div.grid (first args) args]
    [:div.grid args]))

(defn view [args]
  [grid args])
