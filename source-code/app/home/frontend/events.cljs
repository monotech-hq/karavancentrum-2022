
(ns app.home.frontend.events
    (:require [app.home.frontend.prototypes :as prototypes]
              [mid-fruits.map               :refer [dissoc-in]]
              [mid-fruits.vector            :as vector]
              [x.app-core.api               :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn load!
  [db _]
  (dissoc-in db [:home :menu-handler/loaded?]))

(defn loaded
  [db _]
  (assoc-in db [:home :menu-handler/loaded?] true))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn add-menu-item!
  ; @param (map) item-props
  ;  {:group (keyword)(opt)
  ;    Default: :other
  ;   :horizontal-weight (integer)(opt)
  ;    Default: 0
  ;   :icon (keyword)
  ;   :icon-family (keyword)(opt)
  ;   :label (string)
  ;   :on-click (metamorphic-event)}
  ;
  ; @usage
  ;  [:home/add-menu-item! {:label "My item"
  ;                         :icon  :festival
  ;                         :on-click [:router/go-to! "/@app-home/my-item"]}]
  [db [_ item-props]]
  (let [item-props (prototypes/item-props-prototype item-props)]
       (update-in db [:home :menu-handler/menu-items] vector/conj-item item-props)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-db :home/loaded         loaded)
(a/reg-event-db :home/add-menu-item! add-menu-item!)
