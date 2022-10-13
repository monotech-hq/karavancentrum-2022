
(ns app.storage.frontend.media-browser.helpers
    (:require [mid-fruits.format    :as format]
              [mid-fruits.io        :as io]
              [mid-fruits.vector    :as vector]
              [x.app-components.api :as components]
              [x.app-media.api      :as media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn directory-item->icon-family
  [{:keys [items]}]
  (if (vector/nonempty? items) :material-icons-filled :material-icons-outlined))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn directory-item->size
  [{:keys [size items]}]
  (str (-> size io/B->MB format/decimals (str " MB\u00A0\u00A0\u00A0|\u00A0\u00A0\u00A0"))
       (components/content {:content :n-items :replacements [(count items)]})))

(defn file-item->size
  [{:keys [size]}]
  (-> size io/B->MB format/decimals (str " MB")))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn directory-item->header
  [directory-item]
  (let [icon-family (directory-item->icon-family directory-item)]
       {:icon :folder :icon-family icon-family}))

(defn file-item->header
  [{:keys [alias filename]}]
  {:icon :insert_drive_file
   :thumbnail (if (io/filename->image? alias)
                  (media/filename->media-thumbnail-uri filename))})
