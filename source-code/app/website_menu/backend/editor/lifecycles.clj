
(ns app.website-menu.backend.editor.lifecycles
    (:require [engines.file-editor.api]
              [x.core.api :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:file-editor/init-editor! :website-menu.editor
                                              {:base-route  "/@app-home/website-menu"
                                               :handler-key :website-menu.editor
                                               :on-route    [:website-menu.editor/load-editor!]
                                               :route-title :website-menu}]})
