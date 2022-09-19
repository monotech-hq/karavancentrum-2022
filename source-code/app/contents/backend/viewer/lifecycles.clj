
(ns app.contents.backend.viewer.lifecycles
    (:require [plugins.item-viewer.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-viewer/init-viewer! :contents.content-viewer
                                              {:base-route      "/@app-home/contents"
                                               :collection-name "contents"
                                               :handler-key     :contents.content-viewer
                                               :item-namespace  :content
                                               :on-route        [:contents.content-viewer/load-viewer!]
                                               :route-title     :contents}]})
