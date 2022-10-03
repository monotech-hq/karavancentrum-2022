
(ns site.karavancentrum.pages.main-page.frontend.subs
  (:require
    [re-frame.api :as r]))


;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn site-get [db [_ path]]
  (get-in db (concat [:site] path)))

(defn filter-contains? [db [_ path item]]
  (contains? (get-in db path) item))

(defn filter-disabled? [db [_ id]]
  (empty? (filter #(= id (:vehicle/type %)) (get-in db [:site :vehicles]))))

(defn vehicles [db _]
  (let [filters (get db :main-page.filters)]
    (filter #(contains? filters (:vehicle/type %))
            (get-in db [:site :vehicles]))))

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Resgistry ----

(r/reg-sub :site/get site-get)
(r/reg-sub :site/vehicles vehicles)
(r/reg-sub :main-page.filters/contains? filter-contains?)
(r/reg-sub :main-page.filters/disabled? filter-disabled?)

;; ---- Resgistry ----
;; -----------------------------------------------------------------------------
