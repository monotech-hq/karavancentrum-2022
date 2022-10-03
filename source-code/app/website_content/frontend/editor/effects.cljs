
(ns app.website-content.frontend.editor.effects
  (:require [app.website-content.frontend.editor.views :as views]
            [x.app-core.api                            :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :website-content.editor/load-editor!
  (fn [{:keys [db]} _]
      {:dispatch-n [[:gestures/init-view-handler! :website-content.editor
                                                  {:default-view-id :renting}]
                    [:ui/render-surface! :website-content.editor/view
                                         {:content #'views/view}]]}))
