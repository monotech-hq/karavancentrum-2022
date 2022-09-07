
(ns app.website-config.frontend.lifecycles
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:group    :website
                                       :icon     :tune
                                       :label    :website-config
                                       :on-click [:router/go-to! "/@app-home/website-config"]}]})
