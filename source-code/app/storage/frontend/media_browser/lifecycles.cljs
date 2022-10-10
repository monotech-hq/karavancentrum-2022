
(ns app.storage.frontend.media-browser.lifecycles
    (:require [app.home.frontend.api]
              [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:home.screen/add-menu-item! {:group       :website
                                              :icon        :folder
                                              :icon-color  "#8655b1"
                                              :icon-family :material-icons-outlined
                                              :label       :file-storage
                                              :on-click    [:router/go-to! "/@app-home/storage"]
                                              :horizontal-weight 0}]})
