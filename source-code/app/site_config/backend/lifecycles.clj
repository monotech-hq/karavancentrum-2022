
(ns app.site-config.backend.lifecycles
    (:require [plugins.view-selector.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:view-selector/init-selector! :site-config.view-selector
                                                  {}]})
