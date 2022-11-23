
(ns app.website-impressum.frontend.editor.effects
  (:require [app.website-impressum.frontend.editor.views :as editor.views]
            [re-frame.api                                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :website-impressum.editor/load-editor!
  {:dispatch-n [[:x.gestures/init-view-handler! :website-impressum.editor
                                                {:default-view-id :xxx}]
                [:x.ui/render-surface! :website-impressum.editor/view
                                       {:content #'editor.views/view}]]})
