
(ns site.pages.main-page.backend.lifecycles
  (:require [x.server-core.api :as a]))

(a/reg-lifecycles!
 ::lifecycles
 {:on-server-boot [:router/add-route! :main-page/view
                   {:core-js        "site.js"
                    :route-template "/"
                    :client-event   [:main-page/load!]}]})
