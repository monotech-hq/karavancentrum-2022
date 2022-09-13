
(ns app.storage.backend.capacity-handler.side-effects
    (:require [app.storage.backend.core.config :as core.config]
              [mongo-db.api                    :as mongo-db]
              [x.server-core.api               :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-max-upload-size
  []
  @(a/subscribe [:core/get-server-config-item :max-upload-size]))

(defn get-total-capacity
  []
  @(a/subscribe [:core/get-server-config-item :storage-capacity]))

(defn get-used-capacity
  []
  (if-let [root-directory-document (mongo-db/get-document-by-id "storage" core.config/ROOT-DIRECTORY-ID)]
          (get root-directory-document :media/size)))

(defn get-free-capacity
  []
  (if-let [used-capacity (get-used-capacity)]
          (if-let [total-capacity @(a/subscribe [:core/get-server-config-item :storage-capacity])]
                  (- total-capacity used-capacity))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-capacity-details
  []
  {:media/max-upload-size (get-max-upload-size)
   :media/total-capacity  (get-total-capacity)
   :media/used-capacity   (get-used-capacity)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn capacity-limit-exceeded?
  [size]
  (> size (get-free-capacity)))
