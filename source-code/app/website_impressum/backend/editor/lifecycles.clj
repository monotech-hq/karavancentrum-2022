
(ns app.website-impressum.backend.editor.lifecycles
    (:require [engines.file-editor.api]
              [x.core.api :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:file-editor/init-editor! :website-impressum.editor
                                              {:base-route  "/@app-home/website-impressum"
                                               :handler-key :website-impressum.editor
                                               :on-route    [:website-impressum.editor/load-editor!]
                                               :route-title :website-impressum}]})
