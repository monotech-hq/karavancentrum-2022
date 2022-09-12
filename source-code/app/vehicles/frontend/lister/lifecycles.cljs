
(ns app.vehicles.frontend.lister.lifecycles
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:group    :vehicles
                                       :icon     :list
                                       :label    :vehicles
                                       :on-click [:router/go-to! "/@app-home/vehicles"]}]})
