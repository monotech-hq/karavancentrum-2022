
(ns app.home.frontend.events
    (:require [mid-fruits.vector :as vector]
              [x.app-core.api    :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-db
  :home/add-menu-item!
  ; @param (map) item-props
  ;  {:label (string)
  ;   :icon (keyword)
  ;   :on-click (metamorphic-event)}
  ;
  ; @usage
  ;  [:home/add-menu-item! {:label "My item"
  ;                         :icon  :festival
  ;                         :on-click [:router/go-to! "/@app-home/my-item"]}]
  (fn [db [_ item-props]]
      (update db :home-menu vector/conj-item item-props)))
