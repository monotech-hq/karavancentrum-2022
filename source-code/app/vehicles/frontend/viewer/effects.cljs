
(ns app.vehicles.frontend.viewer.effects
    (:require [app.vehicles.frontend.viewer.views :as viewer.views]
              [x.app-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :vehicles.vehicle-viewer/load!
  (fn [_ [_ view-id]]
      {:dispatch-n [[:gestures/init-view-handler! :vehicles.vehicle-viewer
                                                  {:default-view-id (or view-id :overview)}]
                    [:ui/render-surface! :vehicles.vehicle-viewer/view
                                         {:content #'viewer.views/view}]]}))
