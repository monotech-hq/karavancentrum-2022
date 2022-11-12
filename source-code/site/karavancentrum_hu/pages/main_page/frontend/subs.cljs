
(ns site.karavancentrum-hu.pages.main-page.frontend.subs
    (:require [re-frame.api :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

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

(defn no-filter-enabled?
  [db _]
  (-> db :main-page.filters empty?))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-sub :site/get                             site-get)
(r/reg-sub :site/vehicles                        vehicles)
(r/reg-sub :main-page.filters/contains?          filter-contains?)
(r/reg-sub :main-page.filters/disabled?          filter-disabled?)
(r/reg-sub :main-page.filters/no-filter-enabled? no-filter-enabled?)
