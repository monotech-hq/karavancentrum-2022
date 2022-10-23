
(ns site.common.frontend.fragment-sensor.views
    (:require [dom.api                                      :as dom]
              [reagent.api                                  :as reagent]
              [site.common.frontend.fragment-sensor.helpers :as fragment-sensor.helpers]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- fragment-sensor
  ; @param (keyword) fragment-id
  [fragment-id]
  [:div {:id fragment-id :style {:height "1px" :width "100%"}}])

(defn view
  ; @param (keyword) fragment-id
  ;
  ; @usage
  ;  [common/fragment-sensor :about-us]
  [fragment-id]
  (let [intersect-f (fragment-sensor.helpers/intersect-f fragment-id)]
       (reagent/lifecycles {:component-did-mount (fn [] (let [sensor-element (-> fragment-id name dom/get-element-by-id)]
                                                             (dom/setup-intersection-observer! sensor-element intersect-f)))
                            :reagent-render      (fn [] [fragment-sensor fragment-id])})))
