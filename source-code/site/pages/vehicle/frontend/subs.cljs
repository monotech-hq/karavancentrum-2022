
(ns site.pages.vehicle.frontend.subs
  (:require
    [x.app-core.api :as a]))


;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn get-vehicle [db [_]]
  (let [id       (get-in db [:selected-vehicle])
        vehicles (get-in db [:site :vehicles])]
    (first (filter #(= id (:vehicle/id %)) vehicles))))

(defn get-all-by-link [db [_]]
  (let [link     (get-in db [:router :route-handler/meta-items :route-path-params :link])
        vehicles (get-in db [:site :vehicles])]
    (filter #(= link (:vehicle/link %)) vehicles)))

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Resgistry ----

(a/reg-sub :vehicle/get get-vehicle)
(a/reg-sub :vehicle/get-all-by-link get-all-by-link)

;; ---- Resgistry ----
;; -----------------------------------------------------------------------------
