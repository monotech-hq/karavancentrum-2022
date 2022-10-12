
(ns app.views.frontend.privacy-policy.effects
    (:require [app.views.frontend.privacy-policy.events :as privacy-policy.events]
              [app.views.frontend.privacy-policy.views  :as privacy-policy.views]
              [re-frame.api                             :as r :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :views.privacy-policy/load-page!
  (fn [{:keys [db]} _]
      {:db             (r privacy-policy.events/load-page! db)
       :dispatch-n     [[:ui/simulate-process!]
                        [:views.privacy-policy/render-page!]]
       :dispatch-later [{:ms 500 :dispatch [:views.privacy-policy/page-loaded]}]}))

(r/reg-event-fx :views.privacy-policy/render-page!
  [:ui/render-surface! :views.privacy-policy/view
                       {:content #'privacy-policy.views/view}])
