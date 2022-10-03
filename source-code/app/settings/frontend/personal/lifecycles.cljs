
(ns app.settings.frontend.personal.lifecycles
    (:require [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:disabled?   true
                                       :group       :settings
                                       :icon        :person
                                       :icon-color  "#55a3b1"
                                       :icon-family :material-icons-outlined
                                       :label       :user-profile
                                       :on-click    [:router/go-to! "/@app-home/settings/personal"]
                                       :horizontal-weight 0}]})
