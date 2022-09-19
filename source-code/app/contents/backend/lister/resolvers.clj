
(ns app.contents.backend.lister.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]
              [plugins.item-lister.api               :as item-lister]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-items-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (map)
  ;  {:document-count (integer)
  ;   :documents (namespaced maps in vector)}
  [env resolver-props]
  (let [get-pipeline   (item-lister/env->get-pipeline   env :contents.content-lister)
        count-pipeline (item-lister/env->count-pipeline env :contents.content-lister)]
       {:documents      (mongo-db/get-documents-by-pipeline   "contents"   get-pipeline)
        :document-count (mongo-db/count-documents-by-pipeline "contents" count-pipeline)}))

(defresolver get-items
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (namespaced map)
             ;  {:contents.content-lister/get-items (map)
             ;    {:document-count (integer)
             ;     :documents (namespaced maps in vector)}}
             [env resolver-props]
             {:contents.content-lister/get-items (get-items-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-items])

(pathom/reg-handlers! ::handlers HANDLERS)
