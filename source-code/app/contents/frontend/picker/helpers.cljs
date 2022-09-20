
(ns app.contents.frontend.picker.helpers
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-content-query
  ; @param (keyword) content-id
  [content-id]
  [`(~:contents.content-picker/get-content ~{:content-id content-id})])
