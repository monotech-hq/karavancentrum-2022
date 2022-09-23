
(ns site.pages.vehicle.frontend.subs
  (:require
    [x.app-core.api :as a]))


;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn get-vehicle [db [_]]
  (let [id (get-in db [:router :route-handler/meta-items :route-path-params :name])
        vehicles (get-in db [:site :vehicles])]
    (first (filter #(= id (:vehicle/id %)) vehicles))))

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Resgistry ----

(a/reg-sub :vehicle/get get-vehicle)

;; ---- Resgistry ----
;; -----------------------------------------------------------------------------
