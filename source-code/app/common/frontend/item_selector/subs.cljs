
(ns app.common.frontend.item-selector.subs
    (:require [plugins.item-lister.api :as item-lister]
              [x.app-core.api          :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn autosaving?
  ; @param (keyword) selector-id
  ;
  ; @usage
  ;  (r common/autosaving? db :my-selector)
  ;
  ; @return (boolean)
  [db [_ selector-id]]
  (r item-lister/get-meta-item db selector-id :autosave-id))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
;  [:item-selector/autosaving? :my-selector]
(a/reg-sub :item-selector/autosaving? autosaving?)
 
