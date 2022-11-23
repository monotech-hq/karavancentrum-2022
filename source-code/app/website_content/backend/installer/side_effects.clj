
(ns app.website-content.backend.installer.side-effects
    (:require [app.website-content.backend.handler.config :as handler.config]
              [io.api                                     :as io]
              [x.core.api                                 :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- installer
  []
  (io/create-edn-file! handler.config/WEBSITE-CONTENT-FILEPATH))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-installer! ::installer installer)
