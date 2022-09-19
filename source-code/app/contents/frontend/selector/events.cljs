
(ns app.contents.frontend.selector.events
    (:require [app.contents.frontend.selector.subs :as selector.subs]
              [mid-fruits.map                      :refer [dissoc-in]]
              [x.app-core.api                      :as a :refer [r]]
              [x.app-media.api                     :as media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unselect-item!
  [db _]
  (dissoc-in db [:contents :content-selector/selected-item]))

(defn select-item!
  [db [_ {:keys [id]}]]
  (assoc-in db [:contents :content-selector/selected-item] id))

(defn toggle-item-selection!
  [db [_ content-item]]
  (if (r selector.subs/item-selected? db content-item)
      (r unselect-item!               db content-item)
      (r select-item!                 db content-item)))

(defn set-saving-mode!
  [db _]
  (assoc-in db [:contents :content-selector/meta-items :saving?] true))

(defn store-selected-item!
  [db _]
  (let [value-path (get-in db [:contents :content-selector/selector-props :value-path])]
       (let [selected-item (get-in db [:contents :content-selector/selected-item])]
            (assoc-in db value-path selected-item))))

(defn discard-selection!
  [db _]
  (dissoc-in db [:contents :content-selector/selected-item]))

(defn load-selector!
  [db [_ _ {:keys [value-path] :as selector-props}]]
  ; A load-selector! függvény kitörli az utoljára megnyitott media-selector meta-adatait (pl. {:saving? ...})
  (let [saved-selection (get-in db value-path)]
       (cond-> db (some?   saved-selection) (assoc-in  [:contents :content-selector/selected-item] saved-selection)
                  :store-selector-props!    (assoc-in  [:contents :content-selector/selector-props] selector-props)
                  :reset-meta-items!        (dissoc-in [:contents :content-selector/meta-items]))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-db :contents.content-selector/store-selected-item! store-selected-item!)
(a/reg-event-db :contents.content-selector/discard-selection!   discard-selection!)
