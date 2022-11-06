
(ns app.components.frontend.list-item-drag-handle.views
    (:require [elements.api      :as elements]
              [mid-fruits.random :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- list-item-drag-handle-body
  ; @param (keyword) handle-id
  ; @param (map) handle-props
  ;  {:indent (map)(opt)}
  ; @param (map) dnd-kit-props
  ;  {:attributes (map)
  ;   :listeners (map)}
  [_ {:keys [indent]} {:keys [attributes listeners] :as dnd-kit-props}]
  [:div (merge attributes listeners {:style {:align-items "center"
                                             :cursor      "grab"
                                             :display     "flex"
                                             :height      "48px"}})
        [elements/icon {:icon :drag_handle}]])

(defn- list-item-drag-handle
  ; @param (keyword) cell-id
  ; @param (map) cell-props
  ;  {:class (keyword or keywords in vector)(opt)
  ;   :disabled? (boolean)(opt)
  ;   :indent (map)(opt)
  ;   :style (map)(opt)}
  ; @param (map) dnd-kit-props
  [handle-id {:keys [class disabled? indent style] :as handle-props} dnd-kit-props]
  [elements/blank handle-id
                  {:class     class
                   :disabled? disabled?
                   :indent    indent
                   :content   [list-item-drag-handle-body handle-id handle-props dnd-kit-props]
                   :style     style}])

(defn component
  ; @param (keyword)(opt) handle-id
  ; @param (map)(opt) handle-props
  ;  {:class (keyword or keywords in vector)(opt)
  ;   :disabled? (boolean)(opt)
  ;    Default: false
  ;   :indent (map)(opt)
  ;   :style (map)(opt)}
  ; @param (map) dnd-kit-props
  ;  {:attributes (map)
  ;   :indent (map)(opt)
  ;   :listeners (map)}
  ;
  ; @usage
  ;  [list-item-drag-handle {...}]
  ;
  ; @usage
  ;  [list-item-drag-handle :my-handle {...}]
  ([dnd-kit-props]
   [component {} dnd-kit-props])

  ([handle-props dnd-kit-props]
   [component (random/generate-keyword) handle-props dnd-kit-props])

  ([handle-id handle-props dnd-kit-props]
   [list-item-drag-handle handle-id handle-props dnd-kit-props]))
