
(ns app.website-content.backend.editor.lifecycles
    (:require [plugins.file-editor.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:file-editor/init-editor! :website-content
                                              {:base-route  "/@app-home/website-content"
                                               :handler-key :website-content
                                               :on-route    [:website-content/load-editor!]
                                               :route-title :website-content}]})
