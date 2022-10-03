
(ns app.common.frontend.item-selector.events
    (:require [mid-fruits.candy        :refer [return]]
              [mid-fruits.map          :refer [dissoc-in]]
              [mid-fruits.vector       :as vector]
              [plugins.item-lister.api :as item-lister]
              [x.app-core.api          :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn import-selection!
  ; @param (keyword) selector-id
  ; @param (map) selector-props
  ;  {:value-path (vector)}
  ;
  ; @usage
  ;  (r common/import-selection! db :my-selector {...})
  ;
  ; @return (map)
  [db [_ selector-id {:keys [import-id-f value-path]}]]
  (let [stored-selection (get-in db value-path)]
       (cond (vector? stored-selection) (r item-lister/import-selection!        db selector-id (vector/->items stored-selection import-id-f))
             (some?   stored-selection) (r item-lister/import-single-selection! db selector-id (import-id-f    stored-selection))
             :else                      (return db))))

(defn export-selection!
  ; @param (keyword) selector-id
  ;
  ; @usage
  ;  (r common/export-selection! db :my-selector)
  ;
  ; @return (map)
  [db [_ selector-id]]
  (if-let [multi-select? (r item-lister/get-meta-item db selector-id :multi-select?)]
          ; ...
          (let [value-path     (r item-lister/get-meta-item    db selector-id :value-path)
                export-id-f    (r item-lister/get-meta-item    db selector-id :export-id-f)
                selected-items (r item-lister/export-selection db selector-id)
                exported-items (vector/->items selected-items export-id-f)]
               (assoc-in db value-path exported-items))
          ; ...
          (let [value-path    (r item-lister/get-meta-item           db selector-id :value-path)
                export-id-f   (r item-lister/get-meta-item           db selector-id :export-id-f)
                selected-item (r item-lister/export-single-selection db selector-id)
                exported-item (export-id-f selected-item)]
               (assoc-in db value-path exported-item))))

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
  (as-> db % (r import-selection!     % selector-id selector-props)
             (r store-selector-props! % selector-id selector-props)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn abort-autosave!
  ; @param (keyword) selector-id
  ;
  ; @return (map)
  [db [_ selector-id]]
  (r item-lister/set-meta-item! db selector-id :autosave-id))
