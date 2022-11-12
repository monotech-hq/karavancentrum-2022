
(ns site.karavancentrum-hu.pages.rent-informations.frontend.effects
    (:require [re-frame.api                                                  :as r]
              [site.karavancentrum-hu.pages.rent-informations.frontend.views :as views]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-fx :rent-informations/render!
  [:ui/render-surface! :rent-informations/view
                       {:content #'views/view}])

(r/reg-event-fx :rent-informations/load!
  {:dispatch-n [[:rent-informations/render!]]})
               ;[:ui/set-title! ...]
