
(ns app.website-config.frontend.editor.lifecycles
    (:require [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:home.screen/add-menu-item! {:group      :website
                                              :icon       :tune
                                              :icon-color "#8655b1"
                                              :label      :website-config
                                              :on-click   [:router/go-to! "/@app-home/website-config"]
                                              :horizontal-weight 2}]})
