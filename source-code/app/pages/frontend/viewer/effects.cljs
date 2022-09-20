
(ns app.pages.frontend.viewer.effects
    (:require [app.pages.frontend.viewer.views :as viewer.views]
              [x.app-core.api                  :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :pages.page-viewer/load-viewer!
  {:dispatch-n [[:gestures/init-view-handler! :pages.page-viewer
                                              {:default-view-id :overview}]
                [:ui/render-surface! :pages.page-viewer/view
                                     {:content #'viewer.views/view}]]})
