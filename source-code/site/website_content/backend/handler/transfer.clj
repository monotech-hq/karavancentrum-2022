
(ns site.website-content.backend.handler.transfer
    (:require [io.api                                      :as io]
              [site.website-content.backend.handler.config :as handler.config]
              [x.core.api                                  :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-content-f
  ; @param (map) request
  [_]
  (io/read-edn-file handler.config/WEBSITE-CONTENT-FILEPATH))

(x.core/reg-transfer! ::transfer-website-content!
  {:data-f      transfer-website-content-f
   :target-path [:site :website-content]})
