
(ns app.storage.frontend.media-browser.lifecycles
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:group    :website
                                       :icon     :folder
                                       :label    :file-storage
                                       :on-click [:router/go-to! "/@app-home/storage"]}]})
