
(ns app.user.frontend.profile-settings.effects
    (:require [app.user.frontend.profile-settings.events :as profile-settings.events]
              [app.user.frontend.profile-settings.views  :as profile-settings.views]
              [re-frame.api                              :as r :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :user.profile-settings/load-page!
  (fn [{:keys [db]} _]
      {:db             (r profile-settings.events/load-page! db)
       :dispatch-n     [[:ui/simulate-process!]
                        [:user.profile-settings/render-page!]]
       :dispatch-later [{:ms 500 :dispatch [:user.profile-settings/page-loaded]}]}))

(r/reg-event-fx :user.profile-settings/render-page!
  [:ui/render-surface! :user.page/view
                       {:content #'profile-settings.views/view}])

 
