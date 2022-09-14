
(ns app.contents.frontend.events
  (:require
    [x.app-sync.api :as sync]
    [x.app-core.api :as a :refer [r]]
    [mid-fruits.map :refer [dissoc-in]]))

;; -----------------------------------------------------------------------------
;; ---- Events ----

(defn request-data! [db _]
  (-> db (dissoc-in [:contents :config-handler/backup-item])
         (dissoc-in [:contents :config-handler/edited-item])))

(defn receive-data! [db [_ contents]]
  (-> db (assoc-in [:contents :config-handler/backup-item] contents)
         (assoc-in [:contents :config-handler/edited-item] contents)))

(defn load! [db _]
  (dissoc-in db [:contents :config-handler/meta-items :loaded?]))

(defn loaded [db _]
  (assoc-in db [:contents :config-handler/meta-items :loaded?] true))

(defn revert-changes! [db _]
  (let [backup-item (get-in db [:contents :config-handler/backup-item])]
    (assoc-in db [:contents :config-handler/edited-item] backup-item)))

(defn save! [db _]
  (let [edited-item (get-in db [:contents :config-handler/edited-item])]
    (assoc-in db [:contents :config-handler/backup-item] edited-item)))

;; ---- Events ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Register ----

(a/reg-event-db :contents/loaded          loaded)
(a/reg-event-db :contents/revert-changes! revert-changes!)
