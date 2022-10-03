
(ns site.karavancentrum.pages.main-page.backend.lifecycles
  (:require [x.server-core.api :as core]))

(core/reg-lifecycles!
 ::lifecycles
 {:on-server-boot [:router/add-route! :main-page/view
                   {:core-js        "site.js"
                    :route-template "/"
                    :client-event   [:main-page/load!]}]})
