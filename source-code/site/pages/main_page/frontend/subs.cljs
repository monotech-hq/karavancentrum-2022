
(ns site.pages.main-page.frontend.subs
  (:require
    [x.app-core.api :as a]))


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

(a/reg-sub :site/get site-get)
(a/reg-sub :site/vehicles vehicles)
(a/reg-sub :main-page.filters/contains? filter-contains?)
(a/reg-sub :main-page.filters/disabled? filter-disabled?)

;; ---- Resgistry ----
;; -----------------------------------------------------------------------------
