
(ns app.website-menu.frontend.editor.lifecycles
    (:require [x.core.api :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-app-boot {:dispatch-n [[:home.screen/add-menu-item! {:group-name  :website
                                                            :icon        :menu
                                                            :icon-color  "#8655b1"
                                                            :icon-family :material-icons-outlined
                                                            :label       :website-menu
                                                            :on-click    [:x.router/go-to! "/@app-home/website-menu"]
                                                            :horizontal-weight 2}]
                              [:home.sidebar/add-menu-item! {:group-name  :website
                                                             :icon        :menu
                                                             :icon-color  "#8655b1"
                                                             :icon-family :material-icons-outlined
                                                             :label       :website-menu
                                                             :on-click    [:x.router/go-to! "/@app-home/website-menu"]
                                                             :vertical-weight 2}]]}})
