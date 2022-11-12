
(ns site.karavancentrum-hu.pages.main-page.frontend.effects
    (:require [re-frame.api                                          :as r]
              [site.karavancentrum-hu.pages.main-page.frontend.views :as views]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-fx :main-page/render-page!
  ; @param (keyword) scroll-target
  (fn [_ [_ scroll-target]]
      {:dispatch-later [{:ms   0 :dispatch [:ui/render-surface! :main-page/view {:content #'views/view}]}
                        {:ms 350 :fx       [:environment/scroll-to-element-top! (name scroll-target)]}]}))

(r/reg-event-fx :main-page/load-page!
  ; @param (keyword) scroll-target
  (fn [_ [_ scroll-target]]
      [:main-page/render-page! scroll-target]))
