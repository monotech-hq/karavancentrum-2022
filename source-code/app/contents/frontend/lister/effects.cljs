
(ns app.contents.frontend.lister.effects
  (:require [app.contents.frontend.lister.views :as lister.views]
            [x.app-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.lister/load-lister!
  [:ui/render-surface! :contents.lister/view
                       {:content #'lister.views/view}])
