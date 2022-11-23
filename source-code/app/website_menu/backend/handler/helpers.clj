
(ns app.website-menu.backend.handler.helpers
    (:require [app.website-menu.backend.handler.config :as handler.config]
              [io.api                                  :as io]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-website-menu
  ; @return (map)
  []
  (io/read-edn-file handler.config/WEBSITE-MENU-FILEPATH))
