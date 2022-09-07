
(ns app.website-config.frontend.effects
  (:require [app.website-config.frontend.events :as events]
            [app.website-config.frontend.views  :as views]
            [x.app-core.api                     :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :website-config/save-changes!
  (fn [{:keys [db]} _]
      (let [website-config (get-in db [:website-config :config-handler/edited-item])]
           {:db       (r events/save-changes! db)
            :dispatch [:sync/send-query! :website-config/synchronizing!
                                         {:display-progress? true
                                          :query [`(website-config/save-changes! ~website-config)]}]})))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :website-config/receive-data!
  (fn [{:keys [db]} [_ {:website-config/keys [get-data]}]]
      {:db (r events/receive-data! db get-data)}))

(a/reg-event-fx
  :website-config/request-data!
  (fn [{:keys [db]} _]
      {:db (r events/request-data! db)
       :dispatch [:sync/send-query! :website-config/synchronizing
                                    {:display-progress? true
                                     :on-success [:website-config/receive-data!]
                                     :query      [:website-config/get-data]}]}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :website-config/load!
  {:dispatch-n [[:website-config/request-data!]
                [:gestures/init-view-handler! :website-config
                                              {:default-view-id :basic-info}]
                [:ui/render-surface! :website-config/view
                                     {:content #'views/view}]]})
