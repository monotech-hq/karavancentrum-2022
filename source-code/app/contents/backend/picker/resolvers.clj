
(ns app.contents.backend.picker.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-content-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (namespaced map)
  [env _]
  (let [content-id (pathom/env->param env :content-id)]
       (mongo-db/get-document-by-id "contents" content-id)))

(defresolver get-content
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (namespaced map)
             ;  {:contents.content-picker/get-content (namespaced map)}
             [env resolver-props]
             {:contents.content-picker/get-content (get-content-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-content])

(pathom/reg-handlers! ::handlers HANDLERS)
