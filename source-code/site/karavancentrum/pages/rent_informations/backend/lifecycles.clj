
(ns site.karavancentrum.pages.rent-informations.backend.lifecycles
    (:require [x.server-core.api :as core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:router/add-route! :rent-informations/route
                    {:js-build       :site
                     :route-template "/berlesi-feltetelek"
                     :client-event   [:rent-informations/load!]}]})
