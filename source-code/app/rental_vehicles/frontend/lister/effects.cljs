
(ns app.rental-vehicles.frontend.lister.effects
    (:require [plugins.item-lister.api]
              [app.rental-vehicles.frontend.lister.views :as lister.views]
              [x.app-core.api                            :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :rental-vehicles.lister/load!
  [:ui/render-surface! :rental-vehicles.lister/view
                       {:content #'lister.views/view}])
