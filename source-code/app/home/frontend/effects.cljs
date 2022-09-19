
(ns app.home.frontend.effects
    (:require [app.home.frontend.events :as events]
              [app.home.frontend.views  :as views]
              [x.app-core.api           :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :home/render!
  [:ui/render-surface! :home/view
                       {:content #'views/view}])

(a/reg-event-fx
  :home/load!
  (fn [{:keys [db]} _]
      {:db             (r events/load! db)
       :dispatch-n     [[:ui/simulate-process!]
                        [:home/render!]
                        [:ui/restore-default-window-title!]]
       :dispatch-later [{:ms 500 :dispatch [:home/loaded]}]}))
