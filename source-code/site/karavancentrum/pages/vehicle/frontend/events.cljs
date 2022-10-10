
(ns site.karavancentrum.pages.vehicle.frontend.events
    (:require [re-frame.api :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn clear-selected-vehicle
  [db _]
  (assoc db :selected-vehicle nil))

(r/reg-event-db :vehicle/clear-selected-vehicle! clear-selected-vehicle)
