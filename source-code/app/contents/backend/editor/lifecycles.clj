
(ns app.contents.backend.editor.lifecycles
    (:require [plugins.item-editor.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-editor/init-editor! :contents.editor
                                              {:base-route      "/@app-home/contents"
                                               :collection-name "contents"
                                               :handler-key     :contents.editor
                                               :item-namespace  :content
                                               :on-route        [:contents.editor/load-editor!]
                                               :route-title     :contents}]})
