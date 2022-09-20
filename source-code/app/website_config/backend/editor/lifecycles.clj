
(ns app.website-config.backend.editor.lifecycles
    (:require [plugins.file-editor.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:file-editor/init-editor! :website-config
                                              {:base-route  "/@app-home/website-config"
                                               :handler-key :website-config
                                               :on-route    [:website-config/load-editor!]
                                               :route-title :website-config}]})
