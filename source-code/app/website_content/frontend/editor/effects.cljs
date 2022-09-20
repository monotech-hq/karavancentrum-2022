
(ns app.website-content.frontend.editor.effects
  (:require [app.website-content.frontend.editor.views :as views]
            [x.app-core.api                            :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :website-content/load-editor!
  (fn [{:keys [db]} _]
      {:dispatch-n [[:gestures/init-view-handler! :website-content
                                                  {:default-view-id :basic-info}]
                    [:ui/render-surface! :website-content/view
                                         {:content #'views/view}]]}))
