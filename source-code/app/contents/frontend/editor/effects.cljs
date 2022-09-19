
(ns app.contents.frontend.editor.effects
    (:require [app.contents.frontend.editor.views :as editor.views]
              [x.app-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.content-editor/load-editor!
  {:dispatch-n [[:gestures/init-view-handler! :contents.content-editor
                                              {:default-view-id :data}]
                [:ui/render-surface! :contents.content-editor/view
                                     {:content #'editor.views/view}]]})
