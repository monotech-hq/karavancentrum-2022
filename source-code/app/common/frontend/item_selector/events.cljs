
(ns app.common.frontend.item-selector.events
    (:require [app.common.frontend.item-selector.subs :as item-selector.subs]
              [mid-fruits.candy                       :refer [return]]
              [mid-fruits.map                         :refer [dissoc-in]]
              [mid-fruits.vector                      :as vector]
              [plugins.item-lister.api                :as item-lister]
              [re-frame.api                           :as r :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn import-selection!
  ; @param (keyword) selector-id
  ; @param (map) selector-props
  ;
  ; @return (map)
  [db [_ selector-id]]
  ; TODO#5060
  (let [imported-selection   (r item-selector.subs/import-selection   db selector-id)
        imported-item-counts (r item-selector.subs/import-item-counts db selector-id)]
       (as-> db % (r item-lister/import-selection! % selector-id imported-selection)
                  (assoc-in % [:plugins :plugin-handler/meta-items selector-id :item-count] imported-item-counts))))

(defn export-selection!
  ; @param (keyword) selector-id
  ;
  ; @return (map)
  [db [_ selector-id]]
  (let [value-path         (r item-lister/get-meta-item           db selector-id :value-path)
        exported-selection (r item-selector.subs/export-selection db selector-id)]
       (assoc-in db value-path exported-selection)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn increase-item-count!
  ; @param (keyword) selector-id
  ; @param (string) item-id
  ;
  ; @return (map)
  [db [_ selector-id item-id]]
  ; TODO#5060 lecserélni majd a core.subs/set-meta-item! függvényre!
  ;
  ; BUG#8001
  (if-let [item-count (get-in db [:plugins :plugin-handler/meta-items selector-id :item-count item-id])]
          (if (< item-count 256)
              (update-in db [:plugins :plugin-handler/meta-items selector-id :item-count item-id] inc)
              (return    db))
          (assoc-in db [:plugins :plugin-handler/meta-items selector-id :item-count item-id] 2)))

(defn decrease-item-count!
  ; @param (keyword) selector-id
  ; @param (string) item-id
  ;
  ; @return (map)
  [db [_ selector-id item-id]]
  ; TODO#5060
  (let [item-count (get-in db [:plugins :plugin-handler/meta-items selector-id :item-count item-id])]
       (if (> item-count 1)
           (update-in db [:plugins :plugin-handler/meta-items selector-id :item-count item-id] dec)
           (return    db))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn store-selector-props!
  ; @param (keyword) selector-id
  ; @param (map) selector-props
  ;  {:autosave? (boolean)(opt)
  ;   :multi-select? (boolean)(opt)
  ;   :on-save (metamorphic-event)(opt)
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  (r common/store-selector-props! db :my-selector {...})
  ;
  ; @return (map)
  [db [_ selector-id selector-props]]
  (letfn [(f [db k v] (r item-lister/set-meta-item! db selector-id k v))]
         (reduce-kv f db selector-props)))

(defn load-selector!
  ; @param (keyword) selector-id
  ; @param (map) selector-props
  ;
  ; @return (map)
  [db [_ selector-id selector-props]]
  (as-> db % (r store-selector-props! % selector-id selector-props)
             (r import-selection!     % selector-id)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn abort-autosave!
  ; @param (keyword) selector-id
  ;
  ; @return (map)
  [db [_ selector-id]]
  (r item-lister/set-meta-item! db selector-id :autosave-id))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
;  [:item-selector/increase-item-count! :my-selector "my-item"]
(r/reg-event-db :item-selector/increase-item-count! increase-item-count!)

; @usage
;  [:item-selector/decrease-item-count! :my-selector "my-item"]
(r/reg-event-db :item-selector/decrease-item-count! decrease-item-count!)
