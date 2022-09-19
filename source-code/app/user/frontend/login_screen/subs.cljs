
(ns app.user.frontend.login-screen.subs
    (:require [x.app-core.api :as a :refer [r]]
              [x.app-sync.api :as sync]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn login-fields-unfilled?
  ; @return (boolean)
  [db _]
  (or (empty? (get-in db [:user :login-screen/login-data :email-address]))
      (empty? (get-in db [:user :login-screen/login-data :password]))))

(defn login-button-disabled?
  ; @return (boolean)
  [db _]
  ; BUG#4677
  ; A Chrome böngésző nem írja bele az autofill értékeket az input mezőkbe,
  ; csak úgy rendereli ki az értéket a mező felett, mint ha az bele lenne írva,
  ; ezért amíg az első valós on-mouse-down esemény nem történik meg,
  ; addig az autofill értékekkel "feltöltött" mezők értéke valójába nil!
  ;
  ; Ha a login-button-disabled? függvény alkalmazná a login-fields-unfilled?
  ; függvényt, akkor ilyen esetekben a felhasználó úgy láthatná, hogy az
  ; autofill kitöltötte a mezőket, miközben a bejelentkező gomb inaktív maradna.
  (or (r sync/listening-to-request? db :user/authenticate!)))
     ;(r login-fields-unfilled?     db)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-sub :user.login-screen/login-button-disabled? login-button-disabled?)
