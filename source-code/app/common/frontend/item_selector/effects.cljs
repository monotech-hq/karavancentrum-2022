
(ns app.common.frontend.item-selector.effects
    (:require [app.common.frontend.item-selector.events     :as item-selector.events]
              [app.common.frontend.item-selector.prototypes :as item-selector.prototypes]
              [app.common.frontend.item-selector.subs       :as item-selector.subs]
              [mid-fruits.random                            :as random]
              [plugins.item-lister.api                      :as item-lister]
              [x.app-core.api                               :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx :item-selector/load-selector!
  ; @param (keyword) selector-id
  ; @param (map) selector-props
  ;  {:autosave? (boolean)(opt)
  ;    Default: false
  ;   :export-item-f (function)(opt)
  ;    Default: (fn [item-id item-count] item-id)
  ;   :import-count-f (function)(opt)
  ;    Default: (fn [_] 1)
  ;   :import-id-f (function)(opt)
  ;    Default: return
  ;   :multi-select? (boolean)(opt)
  ;   :on-change (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja a kiválasztott elem(ek)et.
  ;   :on-save (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja a kiválasztott elem(ek)et.
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [:item-selector/load-selector! :my-selector {...}]
  (fn [{:keys [db]} [_ selector-id selector-props]]
      (let [selector-props (item-selector.prototypes/selector-props-prototype selector-props)]
           {:db (r item-selector.events/load-selector! db selector-id selector-props)})))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx :item-selector/save-selection!
  ; @param (keyword) selector-id
  ; @param (keyword)(opt) autosave-id
  (fn [{:keys [db]} [_ selector-id autosave-id]]
      (if (or (nil? autosave-id)
              (=    autosave-id (r item-lister/get-meta-item db selector-id :autosave-id)))
          {:db          (r item-selector.events/export-selection! db selector-id)
           :dispatch-n [(r item-selector.subs/get-on-save         db selector-id)
                        (r item-selector.subs/get-on-change       db selector-id)]})))

(a/reg-event-fx :item-selector/abort-autosave!
  ; @param (keyword) selector-id
  (fn [{:keys [db]} [_ selector-id]]
      {:db (r item-selector.events/abort-autosave! db selector-id)}))

(a/reg-event-fx :item-selector/autosave-selection!
  ; @param (keyword) selector-id
  (fn [{:keys [db]} [_ selector-id]]
      (let [autosave-id (random/generate-keyword)]
           {:db             (r item-lister/set-meta-item! db selector-id :autosave-id autosave-id)
            :dispatch-later [{:ms 1500 :dispatch [:item-selector/save-selection! selector-id autosave-id]}]})))

(a/reg-event-fx :item-selector/item-clicked
  ; @param (keyword) selector-id
  ; @param (string) item-id
  (fn [{:keys [db]} [_ selector-id item-id]]
      (if-let [multi-select? (r item-lister/get-meta-item db selector-id :multi-select?)]
              ; Ha egyszerre több elemet lehetséges kiválasztani ...
              {:db (r item-lister/toggle-item-selection! db selector-id item-id)}
              ; Ha egyszerre csak egy elemet lehetséges kiválasztani ...
              (let [db (r item-lister/toggle-single-item-selection! db selector-id item-id)]
                   (if-let [autosave? (r item-lister/get-meta-item db selector-id :autosave?)]
                           ; Ha az item-selector {:autosave? true} beállítással van használva ...
                           ; ... az esetlegesen már folyamatban lévő automatikus mentést leállítja.
                           (if-let [item-selected? (r item-lister/item-selected? db selector-id item-id)]
                                   {:dispatch [:item-selector/autosave-selection! selector-id]
                                    :db       (r item-selector.events/abort-autosave! db selector-id)}
                                   {:db       (r item-selector.events/abort-autosave! db selector-id)})
                           ; Ha az item-selector {:autosave? false} beállítással van használva ...
                           {:db db})))))
