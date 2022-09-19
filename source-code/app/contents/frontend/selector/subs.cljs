
(ns app.contents.frontend.selector.subs
    (:require [mid-fruits.candy :refer [return]]
              [x.app-core.api   :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-selected-item
  ; @return (string)
  [db _]
  (get-in db [:contents :content-selector/selected-item]))

(defn get-selected-item-count
  ; @return (integer)
  [db _]
  (if-let [selected-item (r get-selected-item db)]
          (return 1)
          (return 0)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-selected?
  [db [_ {:keys [id]}]]
  (let [selected-item (r get-selected-item db)]
       (= selected-item id)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-sub :contents.content-selector/get-selected-item-count get-selected-item-count)
(a/reg-sub :contents.content-selector/item-selected?          item-selected?)
