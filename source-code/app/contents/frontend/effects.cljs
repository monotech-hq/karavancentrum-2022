
(ns app.contents.frontend.effects
  (:require
    [x.app-core.api            :as a :refer [r]]
    [app.contents.frontend.views :as views]))

(a/reg-event-fx
  :contents/load!
  {:dispatch-n [[:contents/init!]
                [:ui/render-surface! :contents/view
                                     {:content #'views/view}]]})
