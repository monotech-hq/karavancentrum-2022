
(ns site.pages.main-page.frontend.lifecycles
  (:require
    [x.app-core.api :as a]
            
    [site.pages.main-page.frontend.views :as views]))

(a/reg-event-fx
 :main-page/render!
 [:ui/render-surface! :main-page {:content #'views/view}])

(a/reg-event-fx
 :main-page/load!
 {:dispatch-n [
               ;[:ui/set-title!]
               [:main-page/render!]]})
