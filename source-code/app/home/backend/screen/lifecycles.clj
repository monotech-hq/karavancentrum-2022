
(ns app.home.backend.screen.lifecycles
    (:require [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:router/add-route! :home.screen/route
                                       {:core-js        "app.js"
                                        :route-template "/@app-home"
                                        :client-event   [:home.screen/load!]
                                        :restricted?    true}]})
