
(ns app.pages.backend.viewer.lifecycles
    (:require [plugins.item-viewer.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-viewer/init-viewer! :pages.page-viewer
                                              {:base-route      "/@app-home/pages"
                                               :collection-name "pages"
                                               :handler-key     :pages.page-viewer
                                               :item-namespace  :page
                                               :on-route        [:pages.page-viewer/load-viewer!]
                                               :route-title     :pages}]})
