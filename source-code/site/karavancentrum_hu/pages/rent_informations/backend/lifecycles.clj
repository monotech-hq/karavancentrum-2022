
(ns site.karavancentrum-hu.pages.rent-informations.backend.lifecycles
    (:require [x.server-core.api :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:router/add-route! :rent-informations/route
                    {:route-template "/berlesi-feltetelek"
                     :js-build       :site
                     :client-event   [:rent-informations/load!]}]})
