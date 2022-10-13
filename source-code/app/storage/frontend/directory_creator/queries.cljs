
(ns app.storage.frontend.directory-creator.queries)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-create-directory-query
  ; @param (keyword) creator-id
  ; @param (string) directory-name
  ;
  ; @return (vector)
  [db [_ creator-id directory-name]]
  (let [destination-id (get-in db [:storage :directory-creator/meta-items :destination-id])]
       [`(storage.directory-creator/create-directory! ~{:destination-id destination-id :alias directory-name})]))
