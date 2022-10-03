
(ns site.karavancentrum.pages.main-page.frontend.lifecycles
  (:require
    [re-frame.api :as r]
    [site.karavancentrum.pages.main-page.frontend.views :as views]))

(r/reg-event-fx
 :main-page/render!
  (fn [_ [_ scroll-target]]
    {:dispatch     [:ui/render-surface! :main-page {:content [views/view scroll-target]}]}))
     ; :dispatch-later [{:ms 50
                       ; :utils.scroll/scroll-into [scroll-target]}]}))

(r/reg-event-fx
 :main-page/load!
 (fn [_ [_ scroll-target]]
   {:dispatch-n [[:main-page/render! scroll-target]]}))
