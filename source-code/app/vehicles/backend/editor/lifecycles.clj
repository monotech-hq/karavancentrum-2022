
(ns app.vehicles.backend.editor.lifecycles
    (:require [plugins.item-editor.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-editor/init-editor!
                    :vehicles.vehicle-editor
                    {:base-route      "/@app-home/vehicles"
                     :collection-name "vehicles"
                     :handler-key     :vehicles.vehicle-editor
                     :item-namespace  :vehicle
                     :on-route        [:vehicles.vehicle-editor/load!]
                     :route-title     :vehicles}]})
