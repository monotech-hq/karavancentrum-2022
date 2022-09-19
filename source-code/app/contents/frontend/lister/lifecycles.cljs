
(ns app.contents.frontend.lister.lifecycles
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:group    :website
                                       :icon     :fiber_manual_record
                                       :label    :contents
                                       :on-click [:router/go-to! "/@app-home/contents"]
                                       :horizontal-weight 1}]})
