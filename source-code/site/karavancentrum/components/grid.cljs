

(ns site.karavancentrum.components.grid)

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
