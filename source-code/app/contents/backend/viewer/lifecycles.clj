
(ns app.contents.backend.viewer.lifecycles
    (:require [plugins.item-viewer.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:item-viewer/init-viewer! :contents.viewer
                                              {:base-route      "/@app-home/contents"
                                               :collection-name "contents"
                                               :handler-key     :contents.viewer
                                               :item-namespace  :content
                                               :on-route        [:contents.viewer/load-viewer!]
                                               :route-title     :contents}]})
