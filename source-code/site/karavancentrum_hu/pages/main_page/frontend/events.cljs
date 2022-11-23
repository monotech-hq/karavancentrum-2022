
(ns site.karavancentrum-hu.pages.main-page.frontend.events
    (:require [re-frame.api :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn conj-or-disj [coll item]
  (if (contains? coll item)
      (disj      coll item)
      (conj      coll item)))

(defn select [db [_ path item]]
  (let [coll (get-in db path)]
       (assoc-in db path (conj-or-disj coll item))))

(defn init-filters [db _]
  (let [filters #{:alcove-rv :caravan :semi-integrated-rv :trailer :van-rv}
        result  (reduce (fn [m k]
                          (if (empty? (filter #(= k (:vehicle/type %)) (get-in db [:site :rental-vehicles])))
                            m
                            (conj m k)))
                        #{}
                        filters)]
    (assoc db :main-page.filters result)))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(r/reg-event-db :main-page.filters/init!  init-filters)
(r/reg-event-db :main-page.filters/select select)
