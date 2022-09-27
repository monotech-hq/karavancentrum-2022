
(ns app.contents.frontend.picker.helpers
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-content-query
  ; @param (map) picked-content
  ;  {:content/id (string)}
  [{:content/keys [id]}]
  [`(~:contents.content-picker/get-content ~{:content-id id})])
