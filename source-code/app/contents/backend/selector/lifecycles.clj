
(ns app.contents.backend.selector.lifecycles
    (:require [plugins.item-lister.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-lister/init-lister! :contents.content-selector
                                              {:collection-name "contents"
                                               :handler-key     :contents.content-lister
                                               :item-namespace  :content}]})
