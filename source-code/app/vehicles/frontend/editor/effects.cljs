
(ns app.vehicles.frontend.editor.effects
    (:require [app.vehicles.frontend.editor.views :as editor.views]
              [x.app-core.api                       :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :vehicles.editor/load!
  {:dispatch-n [[:gestures/init-view-handler! :vehicles.editor
                                              {:default-view-id :details}]
                [:ui/render-surface! :vehicles.editor/view
                                     {:content #'editor.views/view}]]})
