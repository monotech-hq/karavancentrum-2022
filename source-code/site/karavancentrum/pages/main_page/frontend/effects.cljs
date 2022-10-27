
(ns site.karavancentrum.pages.main-page.frontend.effects
    (:require [re-frame.api                                       :as r]
              [site.karavancentrum.pages.main-page.frontend.views :as views]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-fx :main-page/render!
  ; @param (keyword) scroll-target
  (fn [_ [_ scroll-target]]
      {:dispatch-later [{:ms  0 :dispatch [:ui/render-surface! :main-page {:content #'views/view}]}
                        {:ms 50 :fx       [:environment/scroll-to-element-top! (name scroll-target)]}]}))

(r/reg-event-fx :main-page/load!
  ; @param (keyword) scroll-target
  (fn [_ [_ scroll-target]]
      [:main-page/render! scroll-target]))
