
(ns site.rental-vehicles.backend.handler.transfer
    (:require [app.rental-vehicles.backend.api :as rental-vehicles]
              [x.core.api                      :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-rental-vehicles-f
  ; @param (map) request
  [_]
  (rental-vehicles/get-rental-vehicles))

(x.core/reg-transfer! ::transfer-rental-vehicles!
  {:data-f      transfer-rental-vehicles-f
   :target-path [:site :rental-vehicles]})
