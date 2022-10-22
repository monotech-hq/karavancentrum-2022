
(ns site.karavancentrum.pages.vehicle-page.backend.lifecycles
    (:require [x.server-core.api :as core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:router/add-route! :vehicle-page/route
                    {:js-build       :site
                     :route-template "/berelheto-jarmuveink/:name"
                     :client-event   [:vehicle-page/load!]}]})
