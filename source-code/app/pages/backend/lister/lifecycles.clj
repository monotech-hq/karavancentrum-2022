
(ns app.pages.backend.lister.lifecycles
    (:require [plugins.item-lister.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-lister/init-lister! :pages.page-lister
                                              {:base-route      "/@app-home/pages"
                                               :collection-name "pages"
                                               :handler-key     :pages.page-lister
                                               :item-namespace  :page
                                               :on-route        [:pages.page-lister/load-lister!]
                                               :route-title     :pages}]})
