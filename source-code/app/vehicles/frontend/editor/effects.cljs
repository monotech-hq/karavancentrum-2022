
(ns app.vehicles.frontend.editor.effects
    (:require [app.vehicles.frontend.editor.views :as editor.views]
              [x.app-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :vehicles.vehicle-editor/load!
  {:dispatch-n [[:gestures/init-view-handler! :vehicles.vehicle-editor
                                              {:default-view-id :data}]
                [:ui/render-surface! :vehicles.vehicle-editor/view
                                     {:content #'editor.views/view}]]})
