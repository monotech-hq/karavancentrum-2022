
(ns app.website-content.backend.handler.helpers
    (:require [app.website-content.backend.handler.config :as handler.config]
              [server-fruits.io                           :as io]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-website-content
  ; @return (map)
  []
  (io/read-edn-file handler.config/WEBSITE-CONTENT-FILEPATH))
