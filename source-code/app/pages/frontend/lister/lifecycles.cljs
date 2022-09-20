
(ns app.pages.frontend.lister.lifecycles
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:disabled? true
                                       :group    :content
                                       :icon     :description
                                       :icon-family :material-icons-outlined
                                       :label    :pages
                                       :on-click [:router/go-to! "/@app-home/pages"]
                                       :horizontal-weight 0}]})
