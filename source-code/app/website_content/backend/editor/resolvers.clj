
(ns app.website-content.backend.editor.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]))
              ;[x.server-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- get-content-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (map)
  [env resolver-props])
  ;(io/read-edn-file a/WEBSITE-CONFIG-FILEPATH))

(defresolver get-content
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (namespaced map)
             ;  {:website-content/get-content (map)}
             [env resolver-props]
             {:website-content/get-config (get-content-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-content])

(pathom/reg-handlers! ::handlers HANDLERS)
