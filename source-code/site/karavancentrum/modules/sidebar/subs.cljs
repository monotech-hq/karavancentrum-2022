
(ns site.karavancentrum.modules.sidebar.subs
  (:require
    [re-frame.api :as a]))

;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn get-view-props [db [_ {:keys [id]}]]
    {:id id
     :in (get-in db [::sidebar id :in] false)})

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Registry ----

(r/reg-sub ::get-view-props get-view-props)

;; ---- Registry ----
;; -----------------------------------------------------------------------------
