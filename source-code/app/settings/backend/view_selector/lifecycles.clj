
(ns app.settings.backend.view-selector.lifecycles
    (:require [plugins.view-selector.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:router/add-route! :settings/route
                                       {:client-event   [:settings.view-selector/load-selector!]
                                        :restricted?    true
                                        :route-template "/@app-home/settings"}]})
