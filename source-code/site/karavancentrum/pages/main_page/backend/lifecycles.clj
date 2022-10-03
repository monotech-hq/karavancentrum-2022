
(ns site.karavancentrum.pages.main-page.backend.lifecycles
  (:require [x.server-core.api :as core]))

(core/reg-lifecycles!
 ::lifecycles
 {:on-server-boot [:router/add-routes!
                   {:main-page/view     {:core-js        "site.js"
                                         :route-template "/"
                                         :client-event   [:main-page/load!]}
                    :main-page/contacts {:core-js        "site.js"
                                         :route-template "/kapcsolat"
                                         :client-event   [:main-page/load! "contacts"]}
                    :main-page/renting  {:core-js        "site.js"
                                         :route-template "/berbeadas"
                                         :client-event   [:main-page/load! "renting"]}
                    :main-page/brands  {:core-js        "site.js"
                                        :route-template "/ertekesites"
                                        :client-event   [:main-page/load! "brands--container"]}}]})
