
(ns site.website-config.backend.handler.transfer
    (:require [io.api                                     :as io]
              [site.website-config.backend.handler.config :as handler.config]
              [x.core.api                                 :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-config-f
  ; @param (map) request
  [_]
  (io/read-edn-file handler.config/WEBSITE-CONFIG-FILEPATH))

(x.core/reg-transfer! ::transfer-website-config!
  {:data-f      transfer-website-config-f
   :target-path [:site :website-config]})
