
(ns app.website-contacts.backend.installer.side-effects
    (:require [app.website-contacts.backend.handler.config :as handler.config]
              [io.api                                      :as io]
              [x.core.api                                  :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- installer
  []
  (io/create-edn-file! handler.config/WEBSITE-CONTACTS-FILEPATH))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-installer! ::installer installer)
