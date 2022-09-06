


(ns site.frontend.utils.events
  (:require [x.app-core.api :as a]))


;; ---- Namespace ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn vec->map-keywordize [data keyword-id]
  (reduce (fn [till-now item]
            (assoc till-now (keyword (get item keyword-id)) item))
          {}
          data))

(defn vec->map [data keyword-id]
  (reduce (fn [till-now item]
            (assoc till-now (get item keyword-id) item))
          {}
          data))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Subscribers ----

(a/reg-sub
  :get
  (fn [db [_ the-key]]
    (get db the-key)))

(a/reg-sub
  :get-in
  (fn [db [_ the-keys]]
    (get-in db the-keys)))

(a/reg-sub
  :contains?
  (fn [db [_ key value]]
    (contains? (set (get-in db key)) value)))


;; ---- Subscribers ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Universal events ----

(a/reg-event-db
  :not
  (fn [db [_ key]]
    (if (vector? key)
      (assoc-in db key (not (get-in db key)))
      (assoc db key (not (get db key))))))


(defn conj->set [m key value]
    (let [old-set (set (get-in m key))]
      (assoc-in m key (vec (conj old-set value)))))

(a/reg-event-db
  :conj->set
  (fn [db [_ key new-value]]
    (conj->set db key new-value)))

(defn disj->set [m key value]
 (let [old-set (set (get-in m key))]
   (assoc-in m key (vec (disj old-set value)))))

(a/reg-event-db
  :disj->set
  (fn [db [_ key new-value]]
    (disj->set db key new-value)))

(a/reg-event-db
  :conj-or-disj-to-set
  (fn [db [_ key value]]
    (if (contains? (set (get-in db key)) value)
      (disj->set db key value)
      (conj->set db key value))))

(a/reg-event-db
  :assoc
  (fn [db [_ key value]]
    (assoc db key value)))

(a/reg-event-db
  :assoc-in
  (fn [db [_ keys value]]
    (assoc-in db keys value)))

(defn vassoc-in
  [m [k & ks] v]
  (if ks
    (assoc m k (vassoc-in (get m k []) ks v))
    (assoc m k v)))

(a/reg-event-db
  :vec/assoc-in
  (fn [db [_ keys value]]
    (vassoc-in db keys value)))

(a/reg-event-db
  :merge
  (fn [db [_ key value]]
    (let [item     (get db key)
          new-item (merge item value)]
      (assoc db key new-item))))

(a/reg-event-db
  :merge-deep
  (fn [db [_ key value]]
    (let [item     (get db key)
          new-item (merge-with merge item value)]
      (assoc db key new-item))))

(a/reg-event-db
  :merge-in
  (fn [db [_ keys value]]
    (let [item     (get-in db keys)
          new-item (merge item value)]
      (assoc-in db keys new-item))))

(a/reg-event-db
  :dissoc
  (fn [db [_ key value]]
    (dissoc db key value)))

(a/reg-event-db
  :dissoc-in
  (fn [db [_ keys key]]
    (update-in db keys dissoc key)))

(a/reg-event-db
  :load-data
  (fn [db [_ db-path keyword-id new-data & [no-keyword]]]
    (let [array (if no-keyword
                  (vec->map new-data keyword-id)
                  (vec->map-keywordize new-data keyword-id))]
      (assoc-in db db-path (merge (get-in db db-path) array)))))


;; ---- Events ----
;; -----------------------------------------------------------------------------
