
(ns app.contents.frontend.selector.effects
    (:require [app.contents.frontend.selector.helpers :as selector.helpers]
              [app.contents.frontend.selector.views   :as selector.views]
              [x.app-core.api                         :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.selector/render-selector!
  ; @param (keyword) selector-id
  (fn [_ [_ selector-id]]
      [:ui/render-popup! :contents.selector/view
                         {:content [selector.views/view selector-id]}]))

(a/reg-event-fx
  :contents.selector/load-selector!
  ; @param (keyword)(opt) selector-id
  ; @param (map) selector-props
  ;  {:autosave? (boolean)(opt)
  ;    Default: false
  ;   :multi-select? (boolean)(opt)
  ;    Default: false
  ;   :on-change (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja a kiválasztott elem(ek)et.
  ;   :on-save (metamorphic-event)(opt)
  ;    Az esemény utolsó paraméterként megkapja a kiválasztott elem(ek)et.
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [:contents.selector/load-selector! {...}]
  ;
  ; @usage
  ;  [:contents.selector/load-selector! :my-selector {...}]
  [a/event-vector<-id]
  (fn [_ [_ _ {:keys [autosave? multi-select? on-change on-save value-path]}]]
      {:dispatch-n [[:item-selector/load-selector! :contents.selector
                                                   {:autosave?     autosave?
                                                    :export-id-f   selector.helpers/export-id-f
                                                    :import-id-f   selector.helpers/import-id-f
                                                    :multi-select? multi-select?
                                                    :on-change     on-change
                                                    :on-save       [:contents.selector/selection-saved on-save]
                                                    :value-path    value-path}]
                    [:contents.selector/render-selector!]]}))

(a/reg-event-fx
  :contents.selector/selection-saved
  ; @param (metamorphic-event) on-save
  ; @param (map) exported-item
  (fn [_ [_ on-save exported-item]]
      (let [on-save (a/metamorphic-event<-params on-save exported-item)]
           {:dispatch-n [on-save [:ui/close-popup! :contents.selector/view]]})))
