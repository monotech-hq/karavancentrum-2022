
(ns app.storage.frontend.media-selector.helpers
    (:require [app.storage.frontend.core.config :as core.config]
              [mid-fruits.io                    :as io]
              [x.app-core.api                   :as a]
              [x.app-media.api                  :as media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn import-id-f
  ; @param (string) n
  ;
  ; @example
  ;  (media-selector.helpers/import-id-f "/.../my-item.txt")
  ;  =>
  ;  "my-item"
  ;
  ; @return (string)
  [n]
  ; A tárhely előre feltöltött mintafájlja kivételt képez, mivel a fájlnév
  ; nem a dokumentum azonosítójából származik.
  (let [id (-> n media/media-storage-uri->filename io/filename->basename)]
       (case id "sample" core.config/SAMPLE-FILE-ID id)))

(defn export-id-f
  ; @param (string) n
  ;
  ; @example
  ;  (media-selector.helpers/export-id-f "my-item")
  ;  =>
  ;  "/.../my-item.txt"
  ;
  ; @return (string)
  [n]
  (let [{:keys [filename]} @(a/subscribe [:item-browser/get-item :storage.media-selector n])]
       (media/filename->media-storage-uri filename)))
