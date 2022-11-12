
(ns site.karavancentrum-hu.pages.main-page.backend.lifecycles
    (:require [x.server-core.api :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-server-boot [:router/add-routes! {:main-page/hero     {:js-build       :site
                                                              :route-template "/"
                                                              :client-event   [:main-page/load-page! :hero]}
                                         :main-page/renting  {:js-build       :site
                                                              :route-template "/berbeadas"
                                                              :client-event   [:main-page/load-page! :renting]}
                                         :main-page/brands   {:js-build       :site
                                                              :route-template "/ertekesites"
                                                              :client-event   [:main-page/load-page! :brands]}
                                         :main-page/contacts {:js-build       :site
                                                              :route-template "/kapcsolat"
                                                              :client-event   [:main-page/load-page! :contacts]}}]})
