
(ns app.rental-vehicles.backend.viewer.lifecycles
    (:require [plugins.item-viewer.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:item-viewer/init-viewer! :rental-vehicles.viewer
                                              {:base-route      "/@app-home/rental-vehicles"
                                               :collection-name "rental-vehicles"
                                               :handler-key     :rental-vehicles.viewer
                                               :item-namespace  :vehicle
                                               :on-route        [:rental-vehicles.viewer/load!]
                                               :route-title     :rental-vehicles}]})
