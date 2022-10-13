
(ns app.rental-vehicles.frontend.viewer.effects
    (:require [app.rental-vehicles.frontend.viewer.views :as viewer.views]
              [re-frame.api                              :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :rental-vehicles.viewer/load!
  (fn [_ [_ view-id]]
      {:dispatch-n [[:gestures/init-view-handler! :rental-vehicles.viewer
                                                  {:default-view-id (or view-id :overview)}]
                    [:ui/render-surface! :rental-vehicles.viewer/view
                                         {:content #'viewer.views/view}]]}))
