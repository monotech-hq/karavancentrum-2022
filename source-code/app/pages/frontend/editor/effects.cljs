
(ns app.pages.frontend.editor.effects
    (:require [app.pages.frontend.editor.views :as editor.views]
              [x.app-core.api                  :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :pages.page-editor/load-editor!
  {:dispatch-n [[:gestures/init-view-handler! :pages.page-editor
                                              {:default-view-id :data}]
                [:ui/render-surface! :pages.page-editor/view
                                     {:page #'editor.views/view}]]})
