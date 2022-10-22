
(ns site.karavancentrum.pages.main-page.backend.lifecycles
    (:require [x.server-core.api :as core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:router/add-routes!
                    {:main-page/view     {:js-build       :site
                                          :route-template "/"
                                          :client-event   [:main-page/load!]}
                     :main-page/contacts {:js-build        :site
                                          :route-template "/kapcsolat"
                                          :client-event   [:main-page/load! "contacts"]}
                     :main-page/renting  {:js-build        :site
                                          :route-template "/berbeadas"
                                          :client-event   [:main-page/load! "renting"]}
                     :main-page/brands   {:js-build        :site
                                          :route-template "/ertekesites"
                                          :client-event   [:main-page/load! "brands--container"]}}]})
