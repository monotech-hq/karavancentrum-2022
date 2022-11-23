
(ns site.website-impressum.backend.handler.transfer
    (:require [app.contents.backend.api          :as contents]
              [app.website-impressum.backend.api :as website-impressum]
              [x.core.api                        :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-website-impressum-f
  ; @param (map) request
  [request]
  (let [website-impressum (website-impressum/get-website-impressum)]
       (contents/fill-data request website-impressum)))

(x.core/reg-transfer! ::transfer-website-impressum!
  {:data-f      transfer-website-impressum-f
   :target-path [:site :website-impressum]})
