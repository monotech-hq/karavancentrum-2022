
(ns site.pages.main-page.backend.transfer
  (:require
    [x.server-core.api :as a]
    [server-fruits.io  :as io]
    [mongo-db.api      :as mongo-db]))



;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def project {"vehicle/added-by"    0
              "vehicle/added-at"    0
              "vehicle/modified-by" 0
              "vehicle/modified-at" 0})


;; ---- Configurations ----
;; -----------------------------------------------------------------------------

(defn get-content [item-id]
  (mongo-db/get-document-by-id "contents" item-id))

(defn update-vals [map vals f]
  (reduce #(update-in % [%2] f) map vals))

(a/reg-transfer!
  ::vehicles
  {:data-f (fn [request]
             (mongo-db/get-documents-by-pipeline "vehicles" [{"$match" {:vehicle/visibility true}}
                                                             {"$project" project}]))
   :target-path [:site :vehicles]})

(a/reg-transfer!
  ::contents
  {:data-f (fn [_]
             (let [contents (io/read-edn-file "monoset-environment/website-content.edn")]
               (update-vals contents [:about-us
                                      :rent-informations
                                      :address-data-information
                                      :contacts-data-information]
                            get-content)))
   :target-path [:site :contents]})

(a/reg-transfer!
  ::config
  {:data-f (fn [_]
             (io/read-edn-file "monoset-environment/website-config.edn"))
   :target-path [:site :config]})
