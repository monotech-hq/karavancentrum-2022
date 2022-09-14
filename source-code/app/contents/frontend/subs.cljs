


(ns app.contents.frontend.subs
  (:require
    [mid-fruits.map :as map]
    [x.app-sync.api :as sync]
    [x.app-core.api :as a :refer [r]]))

;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn synchronizing?
  [db _]
  (r sync/listening-to-request? db :contents/synchronizing!))

(defn data-received?
  [db _]
  (get-in db [:contents :config-handler/edited-item]))

(defn loaded? [db _]
  (get-in db [:contents :config-handler/meta-items :loaded?]))

(defn form-changed? [db [_ change-keys]]
  (let [current-item (get-in db [:contents :config-handler/edited-item])
        backup-item  (get-in db [:contents :config-handler/backup-item])]
       (map/items-different? current-item backup-item change-keys)))

(defn changed? [db _]
  (let [current-item (get-in db [:contents :config-handler/edited-item])
        backup-item  (get-in db [:contents :config-handler/backup-item])]
       (map/items-different? current-item backup-item)))

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Register ----

(a/reg-sub :contents/synchronizing?  synchronizing?)
(a/reg-sub :contents/data-received?  data-received?)
(a/reg-sub :contents/loaded?         loaded?)
(a/reg-sub :contents/form-changed?   form-changed?)
(a/reg-sub :contents/changed?        changed?)
