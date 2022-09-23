
(ns site.pages.main-page.frontend.events
  (:require
    [x.app-core.api :as a]))


(defn this-conj [db [_ path item]]
  (assoc-in db path (conj (get-in db path) item)))

(defn this-disj [db [_ path item]]
  (assoc-in db path (disj (get-in db path) item)))

(defn conj-or-disj [coll item]
  (if (contains? coll item)
    (disj coll item)
    (conj coll item)))

(defn select [db [_ path item]]
  (let [coll      (get-in db path)]
    (if (= 1 (count coll))
      (this-conj db [_ path item])
      (assoc-in db path (conj-or-disj coll item)))))

(defn init-filters [db _]
  (let [filters #{:alcove :caravan :semi-integrated :trailer :van}
        result  (reduce (fn [m k]
                          (if (empty? (filter #(= k (:vehicle/type %)) (get-in db [:site :vehicles])))
                            m
                            (conj m k)))
                        #{}
                        filters)]
    (assoc db :main-page.filters result)))

(a/reg-event-db :main-page.filters/init! init-filters)
(a/reg-event-db :main-page.filters/select select)
(a/reg-event-db :main-page.filters/conj this-conj)
(a/reg-event-db :main-page.filters/disj this-disj)
