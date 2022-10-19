
(ns app.storage.frontend.media-selector.helpers
    (:require [app.storage.frontend.core.config :as core.config]
              [io.api                           :as io]
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
  ; @param (map) media-item
  ;  {:filename (string)}
  ; @param (string) media-count
  ;
  ; @example
  ;  (media-selector.helpers/export-item-f "my-media" {...} 1)
  ;  =>
  ;  "/.../my-media.txt"
  ;
  ; @return (string)
  [_ {:keys [filename]} _]
  (if filename (media/filename->media-storage-uri filename)))
