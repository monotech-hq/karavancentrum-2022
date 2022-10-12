
(ns app.views.frontend.terms-of-service.effects
    (:require [app.views.frontend.terms-of-service.events :as terms-of-service.events]
              [app.views.frontend.terms-of-service.views  :as terms-of-service.views]
              [re-frame.api                               :as r :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :views.terms-of-service/load-page!
  (fn [{:keys [db]} _]
      {:db             (r terms-of-service.events/load-page! db)
       :dispatch-n     [[:ui/simulate-process!]
                        [:views.terms-of-service/render-page!]]
       :dispatch-later [{:ms 500 :dispatch [:views.terms-of-service/page-loaded]}]}))

(r/reg-event-fx :views.terms-of-service/render-page!
  [:ui/render-surface! :views.privacy-policy/view
                       {:content #'terms-of-service.views/view}])
