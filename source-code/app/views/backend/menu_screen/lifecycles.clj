
(ns app.views.backend.menu-screen.lifecycles
    (:require [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:views/set-menu-screen! [:views.menu-screen/render!]]})
