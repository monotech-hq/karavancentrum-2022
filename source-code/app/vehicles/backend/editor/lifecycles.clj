
(ns app.vehicles.backend.editor.lifecycles
    (:require [plugins.item-editor.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-editor/init-editor!
                    :vehicles.editor
                    {:base-route      "/@app-home/vehicles"
                     :collection-name "vehicles"
                     :handler-key     :vehicles.editor
                     :item-namespace  :vehicle
                     :on-route        [:vehicles.editor/load!]
                     :route-title     :vehicles}]})
