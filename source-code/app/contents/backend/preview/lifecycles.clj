
(ns app.contents.backend.preview.lifecycles
    (:require [plugins.item-preview.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:item-preview/init-preview! :contents.preview
                                                {:collection-name "contents"
                                                 :handler-key     :contents.preview
                                                 :item-namespace  :content}]})
