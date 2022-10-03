
(ns app.pages.backend.viewer.lifecycles
    (:require [plugins.item-viewer.api]
              [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-viewer/init-viewer! :pages.viewer
                                              {:base-route      "/@app-home/pages"
                                               :collection-name "pages"
                                               :handler-key     :pages.viewer
                                               :item-namespace  :page
                                               :on-route        [:pages.viewer/load-viewer!]
                                               :route-title     :pages}]})
