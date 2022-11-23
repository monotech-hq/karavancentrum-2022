
(ns app.website-menu.frontend.editor.effects
  (:require [app.website-menu.frontend.editor.views :as editor.views]
            [re-frame.api                           :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :website-menu.editor/load-editor!
  {:dispatch-n [[:x.gestures/init-view-handler! :website-menu.editor
                                                {:default-view-id :website-menu}]
                [:x.ui/render-surface! :website-menu.editor/view
                                       {:content #'editor.views/view}]]})
