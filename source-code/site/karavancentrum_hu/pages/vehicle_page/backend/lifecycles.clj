
(ns site.karavancentrum-hu.pages.vehicle-page.backend.lifecycles
    (:require [x.server-core.api :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:router/add-route! :vehicle-page/route
                    {:route-template "/berelheto-jarmuveink/:name"
                     :js-build       :site
                     :client-event   [:vehicle-page/load!]}]})
