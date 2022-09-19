
(ns app.contents.backend.lister.lifecycles
    (:require [plugins.item-lister.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-lister/init-lister! :contents.content-lister
                                              {:base-route      "/@app-home/contents"
                                               :collection-name "contents"
                                               :handler-key     :contents.content-lister
                                               :item-namespace  :content
                                               :on-route        [:contents.content-lister/load-lister!]
                                               :route-title     :contents}]})
