
(ns app.website-config.frontend.editor.effects
  (:require [app.website-config.frontend.editor.views :as views]
            [x.app-core.api                           :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :website-config/load-editor!
  (fn [{:keys [db]} _]
      {:dispatch-n [[:gestures/init-view-handler! :website-config
                                                  {:default-view-id :basic-info}]
                    [:ui/render-surface! :website-config/view
                                         {:content #'views/view}]]}))
