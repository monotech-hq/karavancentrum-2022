
(ns app.views.backend.error-screen.lifecycles
    (:require [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:views/set-error-screen! [:views.error-screen/render!]]})
