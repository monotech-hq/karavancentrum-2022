
(ns app.pages.backend.editor.lifecycles
    (:require [plugins.item-editor.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-editor/init-editor! :pages.page-editor
                                              {:base-route      "/@app-home/pages"
                                               :collection-name "pages"
                                               :handler-key     :pages.page-editor
                                               :item-namespace  :page
                                               :on-route        [:pages.page-editor/load-editor!]
                                               :route-title     :pages}]})
