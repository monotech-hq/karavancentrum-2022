
(ns app.user.backend.login-screen.lifecycles
    (:require [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:views/set-login-screen! [:user.login-screen/render!]]})
