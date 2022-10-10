
(ns site.karavancentrum.pages.vehicle.frontend.effects
    (:require [re-frame.api                                     :as r]
              [site.karavancentrum.pages.vehicle.frontend.views :as views]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-fx :vehicle/render!
  [:ui/render-surface! :vehicle {:on-surface-closed [:vehicle/clear-selected-vehicle!]
                                 :content #'views/view}])

(r/reg-event-fx :vehicle/load!
  {:dispatch-n [[:vehicle/render!]]})
               ;[:ui/set-title! ...]
