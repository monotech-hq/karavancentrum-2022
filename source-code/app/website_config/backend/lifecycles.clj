
(ns app.website-config.backend.lifecycles
    (:require [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:router/add-route! :website-config/route
                                       {:client-event   [:website-config/load!]
                                        :restricted?    true
                                        :route-template "/@app-home/website-config"}]})
