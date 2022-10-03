
(ns site.karavancentrum.pages.vehicle.frontend.subs
  (:require
    [re-frame.api :as r]))


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

(r/reg-sub :vehicle/get get-vehicle)
(r/reg-sub :vehicle/get-all-by-link get-all-by-link)

;; ---- Resgistry ----
;; -----------------------------------------------------------------------------
