
(ns app.settings.frontend.notifications.lifecycles
    (:require [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:disabled?   true
                                       :group       :settings
                                       :icon        :notifications
                                       :icon-color  "#8eb155"
                                       :icon-family :material-icons-outlined
                                       :label       :notifications
                                       :on-click    [:router/go-to! "/@app-home/settings/notifications"]
                                       :horizontal-weight 2}]})
