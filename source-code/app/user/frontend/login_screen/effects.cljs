
(ns app.user.frontend.login-screen.effects
    (:require [app.user.frontend.login-screen.views :as login-screen.views]
              [x.app-core.api                       :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :user.login-screen/login!
  (fn [{:keys [db]} _]
      (let [login-data (get-in db [:user :login-screen/login-data])]
           ;[:user/authenticate! login-data])))
           [:user/authenticate! {:email-address "iroda@karavancentrum.hu"
                                 :password      "Ka31_4px"}])))
 
(a/reg-event-fx
  :user.login-screen/render!
  [:ui/render-surface! :user.login-screen/view
                       {:content #'login-screen.views/view}])
