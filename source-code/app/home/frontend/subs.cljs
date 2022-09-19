
(ns app.home.frontend.subs
    (:require [mid-fruits.candy :refer [return]]
              [x.app-core.api   :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-menu-group-items
  ; @param (keyword) group-name
  ;
  ; @return (maps in vector)
  [db [_ group-name]]
  (let [menu-items (get-in db [:home :menu-handler/menu-items] [])]
       (letfn [(f [group-items {:keys [group] :as menu-item}]
                  (if (=      group-name  group)
                      (conj   group-items menu-item)
                      (return group-items)))]
              (reduce f [] menu-items))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-sub :home/get-menu-group-items get-menu-group-items)
