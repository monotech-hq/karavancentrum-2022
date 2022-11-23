
(ns site.website-config.backend.handler.transfer
    (:require [app.website-config.backend.api :as website-config]
              [x.core.api                     :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-config-f
  ; @param (map) request
  [_]
  (website-config/get-website-config))

(x.core/reg-transfer! ::transfer-website-config!
  {:data-f      transfer-website-config-f
   :target-path [:site :website-config]})
