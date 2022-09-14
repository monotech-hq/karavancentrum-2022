
(ns app.contents.frontend.effects
  (:require
    [x.app-core.api :as a :refer [r]]
    [app.contents.frontend.views :as views]
    [app.contents.frontend.events :as events]))


(a/reg-event-fx
  :contents/save!
  (fn [{:keys [db]} _]
    (let [contents (get-in db [:contents :config-handler/edited-item])]
      {:db       (r events/save! db)
       :dispatch [:pathom/send-query! :contents/synchronizing!
                  {:display-progress? true
                   :query [`(contents/save! ~contents)]}]})))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents/receive-data!
  (fn [{:keys [db]} [_ {:contents/keys [get-data]}]]
      {:db (r events/receive-data! db get-data)}))

(a/reg-event-fx
  :contents/!
  (fn [{:keys [db]} _]
      {:db (r events/request-data! db)
       :dispatch [:pathom/send-query! :website-config/synchronizing
                                      {:display-progress? true
                                       :on-success [:website-config/receive-data!]
                                       :on-stalled [:website-config/loaded]
                                       :query      [:website-config/get-data]}]}))

;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents/load!
  {:dispatch-n [[:contents/init!]
                [:ui/render-surface! :contents/view
                 {:content #'views/view}]]})
