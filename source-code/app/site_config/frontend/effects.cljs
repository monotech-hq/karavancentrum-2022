
(ns app.site-config.frontend.effects
  (:require
            [x.app-core.api            :as a :refer [r]]
            [x.app-elements.api        :as elements]
            [x.app-activities.api      :as activities]
            [x.app-components.api      :as components]

            [mid-fruits.candy :refer [param]]
            [mid-fruits.string         :as string]

            [plugins.view-selector.api :as view-selector]

            [app.site-config.frontend.views :as view]
            [app.site-config.frontend.subs]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

;Saving the modified site-config

(a/reg-event-fx
 :site-config/receive-save!
 {:dispatch-n
  [[:ui/render-bubble! ::notification
                       {:body        #'view/bubble-save-body
                        :autopop?    false
                        :user-close? false}]
   [:router/go-to! "/@app-home"]]})
   ;:stretch-orientation :vertical}])

(a/reg-event-fx
 :site-config/request-save!
 (fn [{:keys [db]} [event-id client]]
   (let [site-config (-> db :site-config :editor-data)]
     [:sync/send-query!
      :clients/request-add-client!
      {:on-success [:site-config/receive-save!]
       :query [`(site-config/save! ~site-config)]}])))

;Getting the actual site-config

(a/reg-event-fx
 :site-config/receive-data!
 (fn [{:keys [db]} [_ response-value]]
   (let [site-config (get response-value :site-config/get)]
     [:db/set-item! [:site-config :editor-data] site-config])))

(a/reg-event-fx
 :site-config/request-data!
 (fn [{:keys [db]} [_]]
   [:sync/send-query!
    :site-config/request
    {:on-success [:site-config/receive-data!]
     :query      [:site-config/get]}]))

;Toggling the selected view

(a/reg-event-fx
 :site-config/toggle-selected-view!
 (fn [{:keys [db]} [_ view-key]]
   [:db/set-item! [:site-config :editor-meta :menu] view-key]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :site-config.view-selector/load-selector!
  {:dispatch-n [[:site-config/request-config!]
                [:ui/render-surface! :site-config.view-selector/view
                                     {:content #'view/view}]]})
