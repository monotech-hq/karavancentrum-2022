
(ns app.vehicles.backend.editor.prototypes
    (:require [app.vehicles.mid.handler.helpers :as handler.helpers]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn vehicle-item-prototype
  ; @param (namespaced map) item
  ;
  ; @return (namespaced map)
  [{:vehicle/keys [name ] :as item}]
  (merge item {:vehicle/link-name (handler.helpers/vehicle-link-name name)}))
