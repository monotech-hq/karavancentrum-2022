
(ns app.rental-vehicles.backend.editor.lifecycles
    (:require [plugins.item-editor.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-editor/init-editor! :rental-vehicles.editor
                                              {:base-route      "/@app-home/rental-vehicles"
                                               :collection-name "rental-vehicles"
                                               :handler-key     :rental-vehicles.editor
                                               :item-namespace  :vehicle
                                               :on-route        [:rental-vehicles.editor/load!]
                                               :route-title     :rental-vehicles}]})
