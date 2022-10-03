
(ns app.vehicles.frontend.viewer.effects
    (:require [app.vehicles.frontend.viewer.views :as viewer.views]
              [x.app-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :vehicles.viewer/load!
  (fn [_ [_ view-id]]
      {:dispatch-n [[:gestures/init-view-handler! :vehicles.viewer
                                                  {:default-view-id (or view-id :overview)}]
                    [:ui/render-surface! :vehicles.viewer/view
                                         {:content #'viewer.views/view}]]}))
