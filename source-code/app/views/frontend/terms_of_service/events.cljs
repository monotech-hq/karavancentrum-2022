
(ns app.views.frontend.terms-of-service.events
    (:require [mid-fruits.map :refer [dissoc-in]]
              [re-frame.api   :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn load-page!
  [db _]
  (dissoc-in db [:views :terms-of-service/page-loaded?]))

(defn page-loaded
  [db _]
  (assoc-in db [:views :terms-of-service/page-loaded?] true))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-db :views.terms-of-service/page-loaded page-loaded)
