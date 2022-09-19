
(ns app.contents.backend.editor.lifecycles
    (:require [plugins.item-editor.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-editor/init-editor! :contents.content-editor
                                              {:base-route      "/@app-home/contents"
                                               :collection-name "contents"
                                               :handler-key     :contents.content-editor
                                               :item-namespace  :content
                                               :on-route        [:contents.content-editor/load-editor!]
                                               :route-title     :contents}]})
