
(ns app.storage.backend.install-handler.lifecycles
    (:require [x.server-core.api :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-server-launch {:fx [:storage/check-install!]}})
