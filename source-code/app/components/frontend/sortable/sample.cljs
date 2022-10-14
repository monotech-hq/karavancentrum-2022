
(ns app.components.frontend.sortable.sample
    (:require [app.components.frontend.sortable.api :as sortable]
              [re-frame.api                         :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-fx :my-order-changed
  (fn [_ [_ reordered-items]]))
      ; ...

(defn my-item-element
  [sortable-id item-dex item {:keys [attributes listeners isDragging] :as dndkit-props}]
  [:div [:div.my-drag-handle (merge attributes listeners)]
        [:div.my-item        (str   item)]])

(defn my-sortable
  []
  [sortable/body {:item-element #'my-item-element
                  :items ["My item" "Your item"]
                  :on-order-changed [:my-order-changed]}])
