
(ns app.contents.frontend.selector.effects
    (:require [app.contents.frontend.selector.helpers :as selector.helpers]
              [app.contents.frontend.selector.views   :as selector.views]
              [x.app-core.api                         :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.content-selector/render-selector!
  ; @param (keyword) selector-id
  (fn [_ [_ selector-id]]
      [:ui/render-popup! :contents.content-selector/view
                         {:content [selector.views/view selector-id]}]))

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
  (fn [_ [_ _ {:keys [value-path]}]]
      {:dispatch-n [[:item-selector/load-selector! :contents.content-selector
                                                   {:autosave?     true
                                                    :export-id-f   selector.helpers/export-id-f
                                                    :import-id-f   selector.helpers/import-id-f
                                                    :multi-select? false
                                                    :on-save       [:ui/close-popup! :contents.content-selector/view]
                                                    :value-path    value-path}]
                    [:contents.content-selector/render-selector!]]}))
