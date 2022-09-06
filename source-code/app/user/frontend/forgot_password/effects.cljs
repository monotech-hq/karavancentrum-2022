
(ns app.user.frontend.forgot-password.effects
    (:require [app.user.frontend.forgot-password.views :as forgot-password.views]
              [x.app-core.api                          :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :user.forgot-password/render!
  [:ui/render-surface! :user.forgot-password/view
                       {:content #'forgot-password.views/view}])
