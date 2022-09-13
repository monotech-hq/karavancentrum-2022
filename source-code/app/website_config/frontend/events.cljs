
(ns app.website-config.frontend.events
  (:require [mid-fruits.map :refer [dissoc-in]]
            [x.app-core.api :as a :refer [r]]
            [x.app-sync.api :as sync]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-data!
  [db _]
  ; A website-config letöltésekor kitörli az adatbázisban egy esetleges korábbi
  ; megtekintéskor megmaradt adatokat, hogy azok ne zavarják meg a subs/data-received?
  ; függvény működését!
  (-> db (dissoc-in [:website-config :config-handler/backup-item])
         (dissoc-in [:website-config :config-handler/edited-item])))

(defn receive-data!
  [db [_ website-config]]
  (-> db (assoc-in [:website-config :config-handler/backup-item] website-config)
         (assoc-in [:website-config :config-handler/edited-item] website-config)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn load!
  [db _]
  (dissoc-in db [:website-config :config-handler/meta-items :loaded?]))

(defn loaded
  [db _]
  (assoc-in db [:website-config :config-handler/meta-items :loaded?] true))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn revert-changes!
  [db _]
  (let [backup-item (get-in db [:website-config :config-handler/backup-item])]
       (assoc-in db [:website-config :config-handler/edited-item] backup-item)))

(defn save-changes!
  [db _]
  ; A website-config mentésekor a róla készült másolatot frissíteni kell,
  ; hogy az adatok megváltozását vizsgáló függvények visszaálljanak alaphelyzetbe
  (let [edited-item (get-in db [:website-config :config-handler/edited-item])]
       (assoc-in db [:website-config :config-handler/backup-item] edited-item)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-db :website-config/loaded          loaded)
(a/reg-event-db :website-config/revert-changes! revert-changes!)
