
(ns site.karavancentrum.pages.vehicle.backend.lifecycles
  (:require [x.server-core.api :as core]))

(core/reg-lifecycles!
 ::lifecycles
 {:on-server-boot [:router/add-route! :vehicle/view
                   {:core-js        "site.js"
                    :route-template "/berelheto-jarmuveink/:name"
                    :client-event   [:vehicle/load!]}]})
