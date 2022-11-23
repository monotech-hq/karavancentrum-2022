
(ns app.website-impressum.backend.handler.helpers
    (:require [app.website-impressum.backend.handler.config :as handler.config]
              [io.api                                       :as io]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-website-impressum
  ; @return (map)
  []
  (io/read-edn-file handler.config/WEBSITE-IMPRESSUM-FILEPATH))
