
(ns app.components.frontend.sortable.helpers
    (:require [app.components.frontend.sortable.state :as state]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn sortable-did-mount-f
  [sortable-id {:keys [items]}]
  (swap! state/SORTABLE-ITEMS assoc sortable-id items))

(defn sortable-will-unmount-f
  [sortable-id sortable-props]
  (swap! state/SORTABLE-ITEMS dissoc sortable-id))
  
(defn sortable-did-update-f
  [sortable-id {:keys [items]}]
  (swap! state/SORTABLE-ITEMS assoc sortable-id items))
