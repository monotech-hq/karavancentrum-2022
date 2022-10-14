
(ns app.common.frontend.list-item-drag-handle.views
    (:require [x.app-elements.api :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- list-item-drag-handle
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) dndkit-props
  ;  {:attributes (map)
  ;   :listeners (map)}
  [_ _ {:keys [attributes listeners] :as dndkit-props}]
  [:div (merge attributes listeners {:style {:margin-left "12px"}})
        [elements/icon {:icon :drag_indicator :style {:cursor :grab}}]])

(defn element
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) dndkit-props
  ;  {:attributes (map)
  ;   :listeners (map)}
  ;
  ; @usage
  ;  [common/list-item-drag-handle :my-lister 0 {...}]
  [lister-id item-dex dndkit-props]
  [list-item-drag-handle lister-id item-dex dndkit-props])
