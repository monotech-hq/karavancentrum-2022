
(ns app.contents.backend.handler.resolvers
    (:require [app.contents.backend.handler.helpers  :as handler.helpers]
              [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [pathom.api                            :as pathom]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-content-f
  ; @param (map) env
  ;  {:request (map)}
  ; @param (map) resolver-props
  ;
  ; @return (string)
  [{:keys [request] :as env} _]
  (let [content-id (pathom/env->param env :content-id)]
       (handler.helpers/get-content request {:content/id content-id})))

(defresolver get-content
             ; @param (map) env
             ; @param (map) resolver-props
             ;  {:content-id (string)}
             ;
             ; @return (map)
             ;  {:contents.handler/get-content (string)}
             [env resolver-props]
             {:contents.handler/get-content (get-content-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-content])

(pathom/reg-handlers! ::handlers HANDLERS)
