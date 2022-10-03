
(ns app.vehicles.backend.viewer.resolvers
    (:require [com.wsscode.pathom3.connect.operation    :as pathom.co :refer [defresolver defmutation]]
              [mongo-db.api                             :as mongo-db]
              [pathom.api                               :as pathom]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-item-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (namespaced map)
  [env _]
  (let [item-id (pathom/env->param env :item-id)]
       (mongo-db/get-document-by-id "vehicles" item-id)))

(defresolver get-item
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (map)
             ;  {:vehicles.viewer/get-item (namespaced map)}
             [env resolver-props]
             {:vehicles.viewer/get-item (get-item-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-item])

(pathom/reg-handlers! ::handlers HANDLERS)
