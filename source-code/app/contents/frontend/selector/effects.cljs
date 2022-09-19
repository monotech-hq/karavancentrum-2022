
(ns app.contents.frontend.selector.effects
    (:require [app.contents.frontend.selector.events :as selector.events]
              [app.contents.frontend.selector.subs   :as selector.subs]
              [app.contents.frontend.selector.views  :as selector.views]
              [x.app-core.api                        :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.content-selector/load-selector!
  ; @param (keyword)(opt) selector-id
  ; @param (map) selector-props
  ;  {:value-path (vector)}
  ;
  ; @usage
  ;  [:contents.content-selector/load-selector! {...}]
  ;
  ; @usage
  ;  [:contents.content-selector/load-selector! :my-selector {...}]
  [a/event-vector<-id]
  ; XXX#1167
  (fn [{:keys [db]} [_ selector-id selector-props]]
      {:db       (r selector.events/load-selector! db selector-id selector-props)
       :dispatch [:contents.content-selector/render-selector!]}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.content-selector/save-selected-item!
  (fn [{:keys [db]} _]
      (let [db (r selector.events/set-saving-mode! db)]
           {:db db :dispatch-later [{:ms         1000
                                     :dispatch-n [[:ui/close-popup! :contents.content-selector/view]
                                                  [:contents.content-selector/store-selected-item!]]}]})))

(a/reg-event-fx
  :contents.content-selector/item-clicked
  (fn [{:keys [db]} [_ content-item]]
      (let [db (r selector.events/toggle-item-selection! db content-item)]
           (if (r selector.subs/item-selected? db content-item)
               {:db db :dispatch-later [{:ms       250
                                         :dispatch [:contents.content-selector/save-selected-item!]}]}
               {:db db}))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.content-selector/render-selector!
  (fn [_ [_ selector-id]]
      [:ui/render-popup! :contents.content-selector/view
                         {:content [selector.views/view selector-id]}]))
