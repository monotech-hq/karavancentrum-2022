
(ns site.karavancentrum.pages.main-page.frontend.lifecycles
  (:require
    [re-frame.api :as r]
    [site.karavancentrum.pages.main-page.frontend.views :as views]))

(r/reg-event-fx
 :main-page/render!
 [:ui/render-surface! :main-page {:content #'views/view}])

(r/reg-event-fx
 :main-page/load!
 {:dispatch-n [
               ;[:ui/set-title!]
               [:main-page/render!]]})
