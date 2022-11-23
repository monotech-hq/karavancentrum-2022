
(ns app.rental-vehicles.backend.handler.helpers
    (:require [app.common.backend.api :as common]
              [mongo-db.api           :as mongo-db]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-rental-vehicles
  ; @example
  ;  (get-rental-vehicles)
  ;  =>
  ;  [{:vehicle/id "my-vehicle"}
  ;   {...}]
  ;
  ; @return (namespaced maps in vector)
  []
  (let [projection (common/get-document-projection :vehicle)]
       (mongo-db/get-collection "rental_vehicles" projection)))
