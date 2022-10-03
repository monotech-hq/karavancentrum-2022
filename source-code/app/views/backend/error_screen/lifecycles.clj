
(ns app.views.backend.error-screen.lifecycles
    (:require [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:views/set-error-screen! [:views.error-screen/render!]]})
