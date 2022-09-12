
(ns app.vehicles.backend.lister.lifecycles
    (:require [plugins.item-lister.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-lister/init-lister! 
                    :vehicles.lister
                    {:base-route      "/@app-home/vehicles"
                     :collection-name "vehicles"
                     :handler-key     :vehicles.lister
                     :item-namespace  :vehicle
                     :on-route        [:vehicles.lister/load!]
                     :route-title     :vehicles}]})
