
(ns app.views.backend.terms-of-service.lifecycles
    (:require [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:router/add-route! :views.terms-of-service/route
                                       {:client-event   [:views.terms-of-service/render!]
                                        :restricted?    true
                                        :route-template "/@app-home/terms-of-service"}]})
