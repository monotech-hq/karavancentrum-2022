
(ns site.karavancentrum-hu.pages.vehicle-page.frontend.subs
    (:require [re-frame.api :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn get-vehicle [db [_]]
  (let [id       (get-in db [:selected-vehicle])
        vehicles (get-in db [:site :vehicles])]
       (first (filter #(= id (:vehicle/id %)) vehicles))))

(defn get-all-by-link
  [db [_]]
  (let [link-name (get-in db [:router :route-handler/meta-items :route-path-params :name])
        vehicles  (get-in db [:site :vehicles])]
       (filter #(= link-name (:vehicle/link-name %)) vehicles)))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-sub :vehicle-page/get-vehicle     get-vehicle)
(r/reg-sub :vehicle-page/get-all-by-link get-all-by-link)
