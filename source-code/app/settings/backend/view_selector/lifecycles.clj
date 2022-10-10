
(ns app.settings.backend.view-selector.lifecycles
    (:require [x.server-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:router/add-route! :settings/route
                                       {:client-event   [:settings.view-selector/load-selector!]
                                        :restricted?    true
                                        :route-template "/@app-home/settings"}]})
