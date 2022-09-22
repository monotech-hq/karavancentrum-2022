
(ns app.vehicles.backend.viewer.lifecycles
    (:require [plugins.item-viewer.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-viewer/init-viewer! :vehicles.vehicle-viewer
                                              {:base-route      "/@app-home/vehicles"
                                               :collection-name "vehicles"
                                               :handler-key     :vehicles.vehicle-viewer
                                               :item-namespace  :vehicle
                                               :on-route        [:vehicles.vehicle-viewer/load!]
                                               :route-title     :vehicles}]})
