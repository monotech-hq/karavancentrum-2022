
(ns site.karavancentrum.pages.main-page.backend.transfer
    (:require [mongo-db.api      :as mongo-db]
              [server-fruits.io  :as io]
              [x.server-core.api :as core]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(def vehicle-projection {"vehicle/added-by"    0
                         "vehicle/added-at"    0
                         "vehicle/modified-by" 0
                         "vehicle/modified-at" 0})

(defn transfer-vehicles-f
  [request]
  (mongo-db/get-documents-by-pipeline "rental-vehicles" [{"$match" {"vehicle/visibility" "*:public"}}
                                                         {"$project" vehicle-projection}]))

(core/reg-transfer! ::transfer-vehicles!
  {:data-f      transfer-vehicles-f
   :target-path [:site :vehicles]})

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-contents-f
  [request]
  (io/read-edn-file "environment/website-content.edn"))

(core/reg-transfer! ::transfer-contents!
  {:data-f      transfer-contents-f
   :target-path [:site :contents]})

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn transfer-config-f
  [request]
  (io/read-edn-file "environment/website-config.edn"))

(core/reg-transfer! ::transfer-config!
  {:data-f      transfer-config-f
   :target-path [:site :config]})
