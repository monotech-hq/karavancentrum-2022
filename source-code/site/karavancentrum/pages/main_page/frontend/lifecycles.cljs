
(ns site.karavancentrum.pages.main-page.frontend.lifecycles
    (:require [re-frame.api                                       :as r]
              [site.karavancentrum.pages.main-page.frontend.views :as views]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-fx :main-page/render!
  (fn [_ [_ scroll-target]]
      [:ui/render-surface! :main-page {:content [views/view scroll-target]}]))

(r/reg-event-fx :main-page/load!
 (fn [_ [_ scroll-target]]
     [:main-page/render! scroll-target]))
