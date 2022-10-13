
(ns app.storage.frontend.media-selector.helpers
    (:require [app.storage.frontend.core.config :as core.config]
              [mid-fruits.io                    :as io]
              [re-frame.api                     :as r]
              [x.app-media.api                  :as media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn import-id-f
  ; @param (string) media-uri
  ;
  ; @example
  ;  (media-selector.helpers/import-id-f "/.../my-media.txt")
  ;  =>
  ;  "my-media"
  ;
  ; @return (string)
  [media-uri]
  ; A tárhely előre feltöltött mintafájlja kivételt képez, mivel a fájlnév
  ; nem a dokumentum azonosítójából származik.
  (let [id (-> media-uri media/media-storage-uri->filename io/filename->basename)]
       (case id "sample" core.config/SAMPLE-FILE-ID id)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn export-item-f
  ; @param (string) media-id
  ; @param (string) media-count
  ;
  ; @example
  ;  (media-selector.helpers/export-item-f "my-media")
  ;  =>
  ;  "/.../my-media.txt"
  ;
  ; @return (string)
  [media-id _]
  (let [{:keys [filename]} @(r/subscribe [:item-browser/get-item :storage.media-selector media-id])]
       (if filename (media/filename->media-storage-uri filename))))
