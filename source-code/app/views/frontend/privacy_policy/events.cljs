
(ns app.views.frontend.privacy-policy.events
    (:require [mid-fruits.map :refer [dissoc-in]]
              [re-frame.api   :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn load-page!
  [db _]
  (dissoc-in db [:views :privacy-policy/page-loaded?]))

(defn page-loaded
  [db _]
  (assoc-in db [:views :privacy-policy/page-loaded?] true))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-db :views.privacy-policy/page-loaded page-loaded)
