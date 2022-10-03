
(ns app.home.backend.lifecycles
    (:require [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:router/add-route! :admin/home
                                       {:core-js        "app.js"
                                        :route-template "/@app-home"
                                        :client-event   [:home/load!]
                                        :restricted?    true}]})
