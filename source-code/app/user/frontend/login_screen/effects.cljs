
(ns app.user.frontend.login-screen.effects
    (:require [app.user.frontend.login-screen.events :as login-screen.events]
              [app.user.frontend.login-screen.views  :as login-screen.views]
              [x.app-core.api                        :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :user.login-screen/authenticate!
  (fn [{:keys [db]} _]
      ; BUG#4677
      (let [login-data (get-in db [:user :login-screen/login-data])]
           [:user/authenticate! login-data])))

(a/reg-event-fx
  :user.login-screen/login!
  (fn [{:keys [db]} _]
      ; BUG#4677
      ; A valódi bejelentkezési esemény késleltetve történik, hogy ha a
      ; login-button gombra kattintás okozza az első on-mouse-down eseményt
      ; az oldalon, akkor az böngésző autofill funkciójának legyen ideje
      ; ténylegesen beleírni az értékeket a mezőkbe.
      {:dispatch-later [{:ms 500 :dispatch [:user.login-screen/authenticate!]}]}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :user.login-screen/render!
  [:ui/render-surface! :user.login-screen/view
                       {:content #'login-screen.views/view}])
