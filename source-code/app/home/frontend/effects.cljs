
(ns app.home.frontend.effects
    (:require [app.home.frontend.views :as views]
              [x.app-core.api          :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :home/render!
  [:ui/render-surface! :home/view
                       {:content #'views/view}])

(a/reg-event-fx
  :home/load!
  {:dispatch-n [[:ui/simulate-process!]
                [:home/render!]]})
