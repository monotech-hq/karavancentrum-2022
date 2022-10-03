
(ns app.website-content.backend.editor.lifecycles
    (:require [plugins.file-editor.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:file-editor/init-editor! :website-content.editor
                                              {:base-route  "/@app-home/website-content"
                                               :handler-key :website-content.editor
                                               :on-route    [:website-content.editor/load-editor!]
                                               :route-title :website-content}]})
