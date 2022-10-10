
(ns app.pages.frontend.lister.lifecycles
    (:require [app.home.frontend.api]
              [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:home.screen/add-menu-item! {:disabled?   true
                                              :group       :content
                                              :icon        :description
                                              :icon-color  "#5564b1"
                                              :icon-family :material-icons-outlined
                                              :label       :pages
                                              :on-click    [:router/go-to! "/@app-home/pages"]
                                              :horizontal-weight 0}]})
