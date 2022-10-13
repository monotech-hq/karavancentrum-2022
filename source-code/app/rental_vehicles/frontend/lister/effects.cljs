
(ns app.rental-vehicles.frontend.lister.effects
    (:require [plugins.item-lister.api]
              [app.rental-vehicles.frontend.lister.views :as lister.views]
              [re-frame.api                              :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :rental-vehicles.lister/load!
  {:dispatch-n [[:db/set-item! [:x] [{:id 1 :label "x"} {:id 2 :label "y"}]]
                [:ui/render-surface! :rental-vehicles.lister/view
                                     {:content #'lister.views/view}]]})
