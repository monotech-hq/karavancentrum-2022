
(ns app.contents.backend.lifecycles
  (:require [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:router/add-route! :contents/route
                                       {:restricted?    true
                                        :client-event   [:contents/load!]
                                        :route-template "/@app-home/contents"}]})
