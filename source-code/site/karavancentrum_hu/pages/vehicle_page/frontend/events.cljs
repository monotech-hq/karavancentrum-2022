
(ns site.karavancentrum-hu.pages.vehicle-page.frontend.events
    (:require [re-frame.api :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn clear-selected-vehicle
  [db _]
  (assoc db :selected-vehicle nil))

(r/reg-event-db :vehicle-page/clear-selected-vehicle! clear-selected-vehicle)
