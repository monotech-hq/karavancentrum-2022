
(ns app.website-impressum.frontend.editor.lifecycles
    (:require [x.core.api :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-app-boot {:dispatch-n [[:home.screen/add-menu-item! {:group-name  :website
                                                            :icon        :dvr
                                                            :icon-color  "#8655b1"
                                                            :icon-family :material-icons-outlined
                                                            :label       :website-impressum
                                                            :on-click    [:x.router/go-to! "/@app-home/website-impressum"]
                                                            :horizontal-weight 4}]
                              [:home.sidebar/add-menu-item! {:group-name  :website
                                                             :icon        :dvr
                                                             :icon-color  "#8655b1"
                                                             :icon-family :material-icons-outlined
                                                             :label       :website-impressum
                                                             :on-click    [:x.router/go-to! "/@app-home/website-impressum"]
                                                             :vertical-weight 4}]]}})
