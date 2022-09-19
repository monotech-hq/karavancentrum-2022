
(ns app.contents.frontend.viewer.effects
    (:require [app.contents.frontend.viewer.views :as viewer.views]
              [x.app-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.content-viewer/load-viewer!
  {:dispatch-n [[:gestures/init-view-handler! :contents.content-viewer
                                              {:default-view-id :overview}]
                [:ui/render-surface! :contents.content-viewer/view
                                     {:content #'viewer.views/view}]]})
