
(ns app.views.frontend.terms-of-service.effects
    (:require [app.views.frontend.terms-of-service.views :as terms-of-service.views]
              [x.app-core.api                            :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx :views.terms-of-service/render!
  [:ui/render-surface! :views.privacy-policy/view
                       {:content #'terms-of-service.views/view}])
