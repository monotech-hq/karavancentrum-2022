
(ns app.website-config.backend.handler.helpers
    (:require [app.website-config.backend.handler.config :as handler.config]
              [server-fruits.io                          :as io]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-website-config
  ; @return (map)
  []
  (io/read-edn-file handler.config/WEBSITE-CONFIG-FILEPATH))
