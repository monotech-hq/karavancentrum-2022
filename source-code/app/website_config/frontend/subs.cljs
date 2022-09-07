
(ns app.website-config.frontend.subs
  (:require [mid-fruits.map :as map]
            [x.app-core.api :as a :refer [r]]
            [x.app-sync.api :as sync]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn synchronizing?
  [db _]
  (r sync/listening-to-request? db :website-config/synchronizing!))

(defn data-received?
  [db _]
  (get-in db [:website-config :config-handler/edited-item]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn form-changed?
  [db [_ change-keys]]
  ; Megvizsgálja, hogy a website-config megadott kulcsú elemei megváltoztak-e
  ; az eredet másolat azonos kulcsú elemeihez képest
  (let [current-item (get-in db [:website-config :config-handler/edited-item])
        backup-item  (get-in db [:website-config :config-handler/backup-item])]
       (map/items-different? current-item backup-item change-keys)))

(defn config-changed?
  [db _]
  ; Megvizsgálja, hogy a website-config megváltozott-e az eredeti másolathoz képest
  (let [current-item (get-in db [:website-config :config-handler/edited-item])
        backup-item  (get-in db [:website-config :config-handler/backup-item])]
       (map/items-different? current-item backup-item)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-sub :website-config/synchronizing?  synchronizing?)
(a/reg-sub :website-config/data-received?  data-received?)
(a/reg-sub :website-config/form-changed?   form-changed?)
(a/reg-sub :website-config/config-changed? config-changed?)
