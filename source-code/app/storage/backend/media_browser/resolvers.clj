
(ns app.storage.backend.media-browser.resolvers
    (:require [app.storage.backend.capacity-handler.side-effects :as capacity-handler.side-effects]
              [com.wsscode.pathom3.connect.operation             :refer [defresolver]]
              [mongo-db.api                                      :as mongo-db]
              [pathom.api                                        :as pathom]
              [plugins.item-browser.api                          :as item-browser]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-item-f
  [env resolver-props]
  (let [item-id (pathom/env->param env :item-id)]
       (if-let [media-item (mongo-db/get-document-by-id "storage" item-id)]
               (if-let [capacity-details (capacity-handler.side-effects/get-capacity-details)]
                       (merge media-item capacity-details)))))

(defresolver get-item
             [env resolver-props]
             {:storage.media-browser/get-item (get-item-f env resolver-props)})

(defn get-items-f
  [env _]
  (let [get-pipeline   (item-browser/env->get-pipeline   env :storage.media-browser)
        count-pipeline (item-browser/env->count-pipeline env :storage.media-browser)]
       {:documents      (mongo-db/get-documents-by-pipeline   "storage"   get-pipeline)
        :document-count (mongo-db/count-documents-by-pipeline "storage" count-pipeline)}))

(defresolver get-items
             [env resolver-props]
             {:storage.media-browser/get-items (get-items-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-item get-items])

(pathom/reg-handlers! ::handlers HANDLERS)
