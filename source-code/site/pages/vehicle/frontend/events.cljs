
(ns site.pages.vehicle.frontend.events
  (:require
    [x.app-core.api :as a]))

(defn clear-selected-vehicle [db _]
  (assoc db :selected-vehicle nil))

(a/reg-event-db :vehicle/clear-selected-vehicle! clear-selected-vehicle)
