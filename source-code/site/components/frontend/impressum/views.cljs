
(ns site.components.frontend.impressum.views
    (:require [elements.api :as elements]
              [random.api   :as random]
              [re-frame.api :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn impressum
  ; @param (keyword) component-id
  ; @param (map) component-props
  ;  {}
  [component-id component-props]
  [:div {:id :mt-impressum}])

(defn component
  ; @param (keyword)(opt) component-id
  ; @param (map) component-props
  ;  {}
  ;
  ; @usage
  ;  [impressum {...}]
  ;
  ; @usage
  ;  [impressum :my-impressum {...}]
  ([component-props]
   [component (random/generate-keyword) component-props])

  ([component-id component-props]
   [impressum component-id component-props]))
