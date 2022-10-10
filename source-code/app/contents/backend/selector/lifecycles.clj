
(ns app.contents.backend.selector.lifecycles
    (:require [plugins.item-lister.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:item-lister/init-lister! :contents.selector
                                              {:collection-name "contents"
                                               :handler-key     :contents.lister
                                               :item-namespace  :content}]})
