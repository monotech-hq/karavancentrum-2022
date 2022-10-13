
(ns utils.scroll.effects
  (:require [re-frame.api      :as r]
            [utils.scroll.core :as utils.scroll]))

(r/reg-fx :fx/scroll-into utils.scroll/scroll-into)

(r/reg-event-fx :utils/scroll-into
  (fn [_ [_ element-id]]
    {:fx/scroll-into [element-id]}))
