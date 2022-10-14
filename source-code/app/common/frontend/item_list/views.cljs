
(ns app.common.frontend.item-list.views
    (:require [mid-fruits.random  :as random]
              [x.app-elements.api :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- item-list
  ; @param (keyword) list-id
  ; @param (map) list-props
  ;  {:item-element (metamorphic-content)}
  [list-id {:keys [item-element items]}]
  (letfn [(f [item-list item-dex {:keys [id] :as item}]
             (conj item-list ^{:key (str id item-dex)}
                              [item-element list-id item-dex item]))]
         (reduce-kv f [:<>] items)))

(defn element
  ; @param (keyword)(opt) list-id
  ; @param (map) list-props
  ;  {:item-element (metamorphic-content)
  ;   :items (vector)}
  ;
  ; @usage
  ;  [common/item-list {...}]
  ;
  ; @usage
  ;  [common/item-list :my-list {...}]
  ;
  ; @usage
  ;  (defn my-item-element [list-id item-dex item])
  ;  [common/item-list :my-list {:item-element #'my-item-element
  ;                              :items        ["a" "b" "c"]}]
  ([list-props]
   [element (random/generate-keyword) list-props])

  ([list-id list-props]
   [item-list list-id list-props]))
