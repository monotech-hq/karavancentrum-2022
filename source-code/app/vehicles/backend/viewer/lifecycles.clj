
(ns app.vehicles.backend.viewer.lifecycles
    (:require [plugins.item-viewer.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-viewer/init-viewer! :vehicles.viewer
                                              {:base-route      "/@app-home/vehicles"
                                               :collection-name "vehicles"
                                               :handler-key     :vehicles.viewer
                                               :item-namespace  :vehicle
                                               :on-route        [:vehicles.viewer/load!]
                                               :route-title     :vehicles}]})
