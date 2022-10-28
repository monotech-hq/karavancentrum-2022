
(ns app.rental-vehicles.frontend.lister.lifecycles
    (:require [x.app-core.api :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-app-boot {:dispatch-n [[:home.screen/add-menu-item! {:group       :vehicles
                                                            :icon        :airport_shuttle
                                                            :icon-color  "#6a7e8e"
                                                            :icon-family :material-icons-outlined
                                                            :label       :rental-vehicles
                                                            :on-click    [:router/go-to! "/@app-home/rental-vehicles"]
                                                            :horizontal-weight 0}]
                              [:home.sidebar/add-menu-item! {:group       :vehicles
                                                             :icon        :airport_shuttle
                                                             :icon-color  "#6a7e8e"
                                                             :icon-family :material-icons-outlined
                                                             :label       :rental-vehicles
                                                             :on-click    [:router/go-to! "/@app-home/rental-vehicles"]
                                                             :vertical-weight 0}]]}})
