
(ns utils.scroll.effects
  (:require
    [x.app-core.api :as a]
    [utils.scroll.core :as utils.scroll]))

(a/reg-fx :fx/scroll-into utils.scroll/scroll-into)

(a/reg-event-fx :utils/scroll-into
  (fn [_ [_ element-id]]
    {:fx/scroll-into [element-id]}))
