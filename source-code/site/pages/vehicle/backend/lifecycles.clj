
(ns site.pages.vehicle.backend.lifecycles
  (:require [x.server-core.api :as a]))

(a/reg-lifecycles!
 ::lifecycles
 {:on-server-boot [:router/add-route! :vehicle/view
                   {:core-js        "site.js"
                    :route-template "/:name"
                    :client-event   [:vehicle/load!]}]})
