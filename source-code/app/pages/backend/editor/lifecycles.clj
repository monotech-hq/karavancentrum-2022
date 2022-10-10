
(ns app.pages.backend.editor.lifecycles
    (:require [plugins.item-editor.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:item-editor/init-editor! :pages.editor
                                              {:base-route      "/@app-home/pages"
                                               :collection-name "pages"
                                               :handler-key     :pages.editor
                                               :item-namespace  :page
                                               :on-route        [:pages.editor/load-editor!]
                                               :route-title     :pages}]})
