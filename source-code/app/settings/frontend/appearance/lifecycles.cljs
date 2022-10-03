
(ns app.settings.frontend.appearance.lifecycles
    (:require [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:disabled?   true
                                       :group       :settings
                                       :icon        :auto_awesome
                                       :icon-color  "#ab55b1"
                                       :icon-family :material-icons-outlined
                                       :label       :appearance
                                       :on-click    [:router/go-to! "/@app-home/settings/appearance"]
                                       :horizontal-weight 1}]})
