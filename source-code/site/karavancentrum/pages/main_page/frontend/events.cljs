
(ns site.karavancentrum.pages.main-page.frontend.events
    (:require [re-frame.api :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn this-conj [db [_ path item]]
  (assoc-in db path (conj (get-in db path) item)))

(defn this-disj [db [_ path item]]
  (assoc-in db path (disj (get-in db path) item)))

(defn conj-or-disj [coll item]
  (if (contains? coll item)
      (disj      coll item)
      (conj      coll item)))

(defn select [db [_ path item]]
  (let [coll      (get-in db path)]
    (if (= 1 (count coll))
      (this-conj db [_ path item])
      (assoc-in db path (conj-or-disj coll item)))))

(defn init-filters [db _]
  (let [filters #{:alcove-rv :caravan :semi-integrated-rv :trailer :van-rv}
        result  (reduce (fn [m k]
                          (if (empty? (filter #(= k (:vehicle/type %)) (get-in db [:site :vehicles])))
                            m
                            (conj m k)))
                        #{}
                        filters)]
    (assoc db :main-page.filters result)))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-db :main-page.filters/init! init-filters)
(r/reg-event-db :main-page.filters/select select)
(r/reg-event-db :main-page.filters/conj this-conj)
(r/reg-event-db :main-page.filters/disj this-disj)
