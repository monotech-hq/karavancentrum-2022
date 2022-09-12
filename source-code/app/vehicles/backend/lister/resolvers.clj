
(ns app.vehicles.backend.lister.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]
              [plugins.item-lister.api               :as item-lister]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-items-f
  [env resolver-props]
  (let [get-pipeline   (item-lister/env->get-pipeline   env :vehicles.lister)
        count-pipeline (item-lister/env->count-pipeline env :vehicles.lister)]
       {:documents      (mongo-db/get-documents-by-pipeline   "vehicles" get-pipeline)
        :document-count (mongo-db/count-documents-by-pipeline "vehicles" count-pipeline)}))

(defresolver get-items
             [env resolver-props]
             {:vehicles.lister/get-items (get-items-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(def HANDLERS [get-items])

(pathom/reg-handlers! ::handlers HANDLERS)
