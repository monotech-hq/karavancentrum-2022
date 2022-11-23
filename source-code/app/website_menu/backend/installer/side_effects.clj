
(ns app.website-menu.backend.installer.side-effects
    (:require [app.website-menu.backend.handler.config :as handler.config]
              [io.api                                  :as io]
              [x.core.api                              :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- installer
  []
  (io/create-edn-file! handler.config/WEBSITE-MENU-FILEPATH))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-installer! ::installer installer)
