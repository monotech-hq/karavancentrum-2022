
(ns app.rental-vehicles.backend.lister.lifecycles
    (:require [plugins.item-lister.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-lister/init-lister! :rental-vehicles.lister
                                              {:base-route      "/@app-home/rental-vehicles"
                                               :collection-name "rental-vehicles"
                                               :handler-key     :rental-vehicles.lister
                                               :item-namespace  :vehicle
                                               :on-route        [:rental-vehicles.lister/load!]
                                               :route-title     :rental-vehicles}]})
