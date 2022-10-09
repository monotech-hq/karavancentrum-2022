
(ns site.karavancentrum.pages.main-page.backend.transfer
  (:require
    [x.server-core.api :as core]
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

(defn get-content
  [{:content/keys [id]}]
  (mongo-db/get-document-by-id "contents" id))

(defn update-vals [map vals f]
  (reduce #(update-in % [%2] f) map vals))

(core/reg-transfer!
  ::vehicles
  {:data-f (fn [request]
             (let [vehicles (mongo-db/get-documents-by-pipeline
                                "rental-vehicles"
                                [{"$match" {"vehicle/visibility" "*:public"}}
                                 {"$project" project}])]
               (mapv #(update-vals % [:vehicle/content] get-content) vehicles)))

   :target-path [:site :vehicles]})

(core/reg-transfer!
  ::contents
  {:data-f (fn [_]
             (let [contents (io/read-edn-file "environment/website-content.edn")]
               (update-vals contents [:about-us
                                      :rent-informations
                                      :address-data-information
                                      :contacts-data-information]
                           get-content)))
   :target-path [:site :contents]})

(core/reg-transfer!
  ::config
  {:data-f (fn [_]
             (io/read-edn-file "environment/website-config.edn"))
   :target-path [:site :config]})
