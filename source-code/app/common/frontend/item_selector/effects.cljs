
(ns app.common.frontend.item-selector.effects
    (:require [app.common.frontend.item-selector.events     :as item-selector.events]
              [app.common.frontend.item-selector.prototypes :as item-selector.prototypes]
              [mid-fruits.random                            :as random]
              [plugins.item-lister.api                      :as item-lister]
              [x.app-core.api                               :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :item-selector/load-selector!
  ; @param (keyword) selector-id
  ; @param (map) selector-props
  ;  {:autosave? (boolean)(opt)
  ;    Default: false
  ;   :export-id-f (function)(opt)
  ;    Default: return
  ;   :import-id-f (function)(opt)
  ;    Default: return
  ;   :multi-select? (boolean)(opt)
  ;   :on-save (metamorphic-event)(opt)
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [:item-selector/load-selector! :my-selector {...}]
  (fn [{:keys [db]} [_ selector-id selector-props]]
      (let [selector-props (item-selector.prototypes/selector-props-prototype selector-props)]
           {:db (r item-selector.events/load-selector! db selector-id selector-props)})))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :item-selector/save-selection!
  ; @param (keyword) selector-id
  ; @param (keyword)(opt) autosave-id
  (fn [{:keys [db]} [_ selector-id autosave-id]]
      (if (or (nil? autosave-id)
              (=    autosave-id (r item-lister/get-meta-item db selector-id :autosave-id)))
          {:db       (r item-selector.events/export-selection! db selector-id)
           :dispatch (r item-lister/get-meta-item              db selector-id :on-save)})))

(a/reg-event-fx
  :item-selector/abort-autosave!
  ; @param (keyword) selector-id
  (fn [{:keys [db]} [_ selector-id]]
      {:db (r item-lister/set-meta-item! db selector-id :autosave-id)}))

(a/reg-event-fx
  :item-selector/autosave-selection!
  ; @param (keyword) selector-id
  (fn [{:keys [db]} [_ selector-id]]
      (let [autosave-id (random/generate-keyword)]
           {:db             (r item-lister/set-meta-item! db selector-id :autosave-id autosave-id)
            :dispatch-later [{:ms 1500 :dispatch [:item-selector/save-selection! selector-id autosave-id]}]})))

(a/reg-event-fx
  :item-selector/item-clicked
  ; @param (keyword) selector-id
  ; @param (string) item-id
  (fn [{:keys [db]} [_ selector-id item-id]]
      (if-let [multi-select? (r item-lister/get-meta-item db selector-id :multi-select?)]
              ; ...
              {:db (r item-lister/toggle-item-selection! db selector-id item-id)}
              ; ...
              (let [db        (r item-lister/toggle-single-item-selection! db selector-id item-id)
                    autosave? (r item-lister/get-meta-item                 db selector-id :autosave?)]
                   (if (r item-lister/item-selected? db selector-id item-id)
                       {:db db :dispatch (if autosave? [:item-selector/autosave-selection! selector-id])}
                       {:db db})))))
