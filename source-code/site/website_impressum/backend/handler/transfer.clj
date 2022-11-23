
(ns site.website-impressum.backend.handler.transfer
    (:require [io.api                                        :as io]
              [site.website-impressum.backend.handler.config :as handler.config]
              [x.core.api                                    :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-impressum-f
  ; @param (map) request
  [_]
  (io/read-edn-file handler.config/WEBSITE-IMPRESSUM-FILEPATH))

(x.core/reg-transfer! ::transfer-website-impressum!
  {:data-f      transfer-website-impressum-f
   :target-path [:site :website-impressum]})
