
(ns app.website-config.backend.installer.side-effects
    (:require [app.website-config.backend.handler.config :as handler.config]
              [io.api                                    :as io]
              [x.core.api                                :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- installer
  []
  (io/create-edn-file! handler.config/WEBSITE-CONFIG-FILEPATH))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-installer! ::installer installer)
