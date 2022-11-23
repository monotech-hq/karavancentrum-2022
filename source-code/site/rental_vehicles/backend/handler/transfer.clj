
(ns site.rental-vehicles.backend.handler.transfer
    (:require [app.contents.backend.api        :as contents]
              [app.rental-vehicles.backend.api :as rental-vehicles]
              [x.core.api                      :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-rental-vehicles-f
  ; @param (map) request
  [request]
  (let [rental-vehicles (rental-vehicles/get-rental-vehicles)]
       (contents/fill-data request rental-vehicles)))

(x.core/reg-transfer! ::transfer-rental-vehicles!
  {:data-f      transfer-rental-vehicles-f
   :target-path [:site :rental-vehicles]})
