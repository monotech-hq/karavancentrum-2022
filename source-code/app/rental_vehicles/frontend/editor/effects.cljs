
(ns app.rental-vehicles.frontend.editor.effects
    (:require [app.rental-vehicles.frontend.editor.views :as editor.views]
              [re-frame.api                              :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :rental-vehicles.editor/load!
  {:dispatch-n [[:gestures/init-view-handler! :rental-vehicles.editor
                                              {:default-view-id :data}]
                [:ui/render-surface! :rental-vehicles.editor/view
                                     {:content #'editor.views/view}]]})
