
(ns app.views.frontend.no-internet-notification.effects
    (:require [app.views.frontend.no-internet-notification.views :as no-internet-notification.views]
              [re-frame.api                                      :as r :refer [r]]
              [x.app-environment.api                             :as environment]
              [x.app-ui.api                                      :as ui]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :views.no-internet-notification/blow-no-internet-bubble?!
  (fn [{:keys [db]} _]
      (if (and (r environment/browser-offline? db))
              ; A UI interface tulajdonsága megszűnt! Vizsgáld a bejelentkezést
              ; vagy a js-build-et!
              ;(r ui/application-interface?    db))
          [:ui/render-bubble! :views.no-internet-notification/notification
                              {:body        #'no-internet-notification.views/body
                               :autoclose?  false
                               :user-close? false}])))
