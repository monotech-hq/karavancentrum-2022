
(ns app.settings.frontend.view-selector.effects
    (:require [app.settings.frontend.view-selector.views :as view-selector.views]
              [x.app-core.api                            :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :settings.view-selector/load-selector!
  [:settings.view-selector/render-selector!])

(a/reg-event-fx
  :settings.view-selector/render-selector!
  [:ui/render-surface! :settings.view-selector/view
                       {:content #'view-selector.views/view}])
