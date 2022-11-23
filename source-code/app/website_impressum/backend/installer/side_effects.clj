
(ns app.website-impressum.backend.installer.side-effects
    (:require [app.website-impressum.backend.handler.config :as handler.config]
              [io.api                                       :as io]
              [x.core.api                                   :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- installer
  []
  (io/create-edn-file! handler.config/WEBSITE-IMPRESSUM-FILEPATH))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-installer! ::installer installer)
