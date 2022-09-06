
(ns app.home.frontend.subs
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-menu-items
  [db _]
  (get-in db [:home-menu] []))

(a/reg-sub :home/get-menu-items get-menu-items)
