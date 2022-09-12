
(ns app.vehicles.frontend.lister.effects
    (:require [app.vehicles.frontend.lister.views :as lister.views]
              [plugins.item-lister.api              :as item-lister]
              [x.app-core.api                       :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :vehicles.lister/load!
  [:ui/render-surface! :vehicles.lister/view
                       {:content #'lister.views/view}])
