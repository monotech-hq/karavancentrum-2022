
(ns site.karavancentrum-hu.pages.main-page.backend.transfer
    (:require [io.api       :as io]
              [mongo-db.api :as mongo-db]
              [x.core.api   :as x.core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(def vehicle-projection {"vehicle/added-by"    0
                         "vehicle/added-at"    0
                         "vehicle/modified-by" 0
                         "vehicle/modified-at" 0})

(defn transfer-vehicles-f
  [request]
  (mongo-db/get-documents-by-pipeline "rental_vehicles" [{"$match" {"vehicle/visibility" "*:public"}}
                                                         {"$project" vehicle-projection}]))

(x.core/reg-transfer! ::transfer-vehicles!
  {:data-f      transfer-vehicles-f
   :target-path [:site :vehicles]})

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-content-f
  [request]
  (io/read-edn-file "environment/website-content.edn"))

(x.core/reg-transfer! ::transfer-content!
  {:data-f      transfer-content-f
   :target-path [:site :content]})

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-config-f
  [request]
  (io/read-edn-file "environment/website-config.edn"))

(x.core/reg-transfer! ::transfer-config!
  {:data-f      transfer-config-f
   :target-path [:site :config]})
