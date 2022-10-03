
(ns app.contents.frontend.lister.lifecycles
    (:require [app.home.frontend.api]
              [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:group       :content
                                       :icon        :article
                                       :icon-color  "#5564b1"
                                       :icon-family :material-icons-outlined
                                       :label       :contents
                                       :on-click    [:router/go-to! "/@app-home/contents"]
                                       :horizontal-weight 1}]})
