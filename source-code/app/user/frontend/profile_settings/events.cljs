
(ns app.user.frontend.profile-settings.events
    (:require [mid-fruits.map :refer [dissoc-in]]
              [re-frame.api   :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn load-page!
  [db _]
  (dissoc-in db [:user :profile-settings/page-loaded?]))

(defn page-loaded
  [db _]
  (assoc-in db [:user :profile-settings/page-loaded?] true))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-db :user.profile-settings/page-loaded page-loaded)
