
(ns app.vehicles.backend.lister.lifecycles
    (:require [plugins.item-lister.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-lister/init-lister! :vehicles.vehicle-lister
                                              {:base-route      "/@app-home/vehicles"
                                               :collection-name "vehicles"
                                               :handler-key     :vehicles.vehicle-lister
                                               :item-namespace  :vehicle
                                               :on-route        [:vehicles.vehicle-lister/load!]
                                               :route-title     :vehicles}]})
