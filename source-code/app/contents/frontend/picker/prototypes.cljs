
(ns app.contents.frontend.picker.prototypes
    (:require [mid-fruits.candy :refer [param]]
              [re-frame.api     :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn selector-props-prototype
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:on-change (metamorphic-event)(opt)
  ;   :on-save (metamorphic-event)(opt)
  ;   :value-path (vector)}
  ;
  ; @return (map)
  ;  {}
  [_ {:keys [on-change on-save value-path]}]
  {:autosave?     true
   :multi-select? false
   :on-change     on-change
   :on-save       on-save
   :value-path    value-path})

(defn preview-props-prototype
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {}
  ;
  ; @return (map)
  ;  {}
  [_ {:keys [disabled? no-item-label value-path]}]
  (let [picked-content @(r/subscribe [:db/get-item value-path])
        content-id      (:content/id picked-content)]
       {:disabled?     disabled?
        :item-id       content-id
        :indent        {:top :m}
        :no-item-label no-item-label}))

(defn picker-props-prototype
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;
  ; @return (map)
  ;  {}
  [_ picker-props]
  (merge {}
         (param picker-props)))
