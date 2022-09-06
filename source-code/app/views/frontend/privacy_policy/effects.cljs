
(ns app.views.frontend.privacy-policy.effects
    (:require [app.views.frontend.privacy-policy.views :as privacy-policy.views]
              [x.app-core.api                          :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :views.privacy-policy/render!
  [:ui/render-surface! :views.privacy-policy/view
                       {:content #'privacy-policy.views/view}])
