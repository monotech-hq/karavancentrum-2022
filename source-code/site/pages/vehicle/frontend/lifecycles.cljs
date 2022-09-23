
(ns site.pages.vehicle.frontend.lifecycles
  (:require
    [x.app-core.api :as a]

    [site.pages.vehicle.frontend.views :as views]))

(a/reg-event-fx
 :vehicle/render!
 [:ui/render-surface! :vehicle {:content #'views/view}])

(a/reg-event-fx
 :vehicle/load!
 {:dispatch-n [
               ;[:ui/set-title!]
               [:vehicle/render!]]})
