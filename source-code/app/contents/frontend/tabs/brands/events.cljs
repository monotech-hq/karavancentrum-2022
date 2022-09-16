
(ns app.contents.frontend.tabs.brands.events
  (:require
    [x.app-core.api :as a :refer [r]]
    [mid-fruits.map :refer [dissoc-in]]))

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn vec-remove
  "Remove elem in coll by index."
  [coll pos]
  (vec (concat (subvec coll 0 pos) (subvec coll (inc pos)))))


;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Events ----

(defn create! [db _]
  (let [items  (get-in db [:contents :config-handler/edited-item :brands])
        result (conj items {:id (str (random-uuid)) :edit? true})]
    (assoc-in db [:contents :config-handler/edited-item :brands] result)))

(defn save! [db [_ index value]]
  ; (let [items  (get-in db [:contents :config-handler/edited-item :brands])
        ; result (conj items {:id (str (random-uuid)) :edit? true})]
    (assoc-in db [:contents :config-handler/edited-item :brands index] value))

(defn remove! [db [_ index]]
  (let [items  (get-in db [:contents :config-handler/edited-item :brands])
        result (vec-remove items index)]
    (assoc-in db [:contents :config-handler/edited-item :brands] result)))

(defn set-state [state path value]
  (swap! state assoc-in path value))

(defn on-type-ended [_ [_ state path value]]
  {::set-state [state path value]})

;; ---- Events ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Register ----

(a/reg-event-db :contents.brand/create!  create!)
(a/reg-event-db :contents.brands/save!   save!)
(a/reg-event-db :contents.brands/remove! remove!)
(a/reg-fx ::set-state set-state)
(a/reg-event-fx :contents.brands/on-type-ended on-type-ended)
