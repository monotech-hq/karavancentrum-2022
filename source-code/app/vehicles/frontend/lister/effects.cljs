
(ns app.vehicles.frontend.lister.effects
    (:require [plugins.item-lister.api]
              [app.vehicles.frontend.lister.views :as lister.views]
              [x.app-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :vehicles.lister/load!
  [:ui/render-surface! :vehicles.lister/view
                       {:content #'lister.views/view}])
