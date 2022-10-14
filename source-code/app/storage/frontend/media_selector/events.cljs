
(ns app.storage.frontend.media-selector.events
    (:require [app.storage.frontend.media-selector.subs :as media-selector.subs]
              [mid-fruits.map                           :refer [dissoc-in]]
              [mid-fruits.vector                        :as vector]
              [plugins.item-browser.api                 :as item-browser]
              [re-frame.api                             :as r :refer [r]]
              [x.app-media.api                          :as media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unselect-file!
  [db [_ {:keys [filename]}]]
  (let [file-uri (media/filename->media-storage-uri filename)]
       (update-in db [:storage :media-selector/selected-items] vector/remove-item file-uri)))

(defn select-file!
  [db [_ {:keys [filename]}]]
  (let [file-uri (media/filename->media-storage-uri filename)]
       (if-let [multiple? (get-in db [:storage :media-selector/selector-props :multiple?])]
               (update-in db [:storage :media-selector/selected-items] vector/conj-item-once file-uri)
               (assoc-in  db [:storage :media-selector/selected-items] [file-uri]))))

(defn toggle-file-selection!
  [db [_ file-item]]
  (if (r media-selector.subs/file-selected? db file-item)
      (r unselect-file!                     db file-item)
      (r select-file!                       db file-item)))

(defn set-saving-mode!
  [db _]
  (assoc-in db [:storage :media-selector/meta-items :saving?] true))

(defn store-selected-items!
  [db _]
  ; XXX#8073
  ; A store-selected-items! függvény a {:value-path [...]} tulajdonságként átadott útvonalra
  ; a kiválasztott elem(ek)et, ...
  ; ... {:multiple? true}  beállítás használatával vektor típusként tárolja.
  ; ... {:multiple? false} beállítás használatával string típusként tárolja.
  (let [value-path (get-in db [:storage :media-selector/selector-props :value-path])]
       (if-let [multiple? (get-in db [:storage :media-selector/selector-props :multiple?])]
               (let [selected-items (get-in db [:storage :media-selector/selected-items])]
                    (assoc-in db value-path selected-items))
               (let [selected-item (get-in db [:storage :media-selector/selected-items 0])]
                    (assoc-in db value-path selected-item)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-db :storage.media-selector/store-selected-items! store-selected-items!)
