
(ns app.common.frontend.item-selector.subs
    (:require [mid-fruits.vector       :as vector]
              [plugins.item-lister.api :as item-lister]
              [x.app-core.api          :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn selection-changed?
  ; @param (keyword) selector-id
  ;
  ; @return (boolean)
  [db [_ selector-id]]
  (let [imported-selection (r item-lister/get-imported-selection db selector-id)]
       (if-let [multi-select? (r item-lister/get-meta-item db selector-id :multi-select?)]
               (let [selected-items (r item-lister/export-selection db selector-id)]
                    (not= imported-selection selected-items))
               (let [selected-item (r item-lister/export-single-selection db selector-id)]
                    (not= imported-selection [selected-item])))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn export-selection
  ; @param (keyword) selector-id
  ;
  ; @return (* or * in vector)
  [db [_ selector-id]]
  (if-let [multi-select? (r item-lister/get-meta-item db selector-id :multi-select?)]
          (let [export-id-f    (r item-lister/get-meta-item    db selector-id :export-id-f)
                selected-items (r item-lister/export-selection db selector-id)]
               (vector/->items selected-items export-id-f))
          (let [export-id-f   (r item-lister/get-meta-item           db selector-id :export-id-f)
                selected-item (r item-lister/export-single-selection db selector-id)]
               (export-id-f selected-item))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-on-change
  ; @param (keyword) selector-id
  ;
  ; @return (metamorphic-event)
  [db [_ selector-id]]
  (if-let [on-change (r item-lister/get-meta-item db selector-id :on-change)]
          (if-let [selection-changed? (r selection-changed? db selector-id)]
                  (let [exported-selection (r export-selection db selector-id)]
                       (a/metamorphic-event<-params on-change exported-selection)))))

(defn get-on-save
  ; @param (keyword) selector-id
  ;
  ; @return (metamorphic-event)
  [db [_ selector-id]]
  (if-let [on-save (r item-lister/get-meta-item db selector-id :on-save)]
          (let [exported-selection (r export-selection db selector-id)]
               (a/metamorphic-event<-params on-save exported-selection))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn autosaving?
  ; @param (keyword) selector-id
  ;
  ; @return (boolean)
  [db [_ selector-id]]
  (r item-lister/get-meta-item db selector-id :autosave-id))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
;  [:item-selector/autosaving? :my-selector]
(a/reg-sub :item-selector/autosaving? autosaving?)
