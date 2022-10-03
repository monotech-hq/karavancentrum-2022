
(ns app.website-content.backend.editor.resolvers
    (:require [app.website-content.backend.handler.config :as handler.config]
              [com.wsscode.pathom3.connect.operation      :refer [defresolver]]
              [pathom.api                                 :as pathom]
              [server-fruits.io                           :as io]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- get-content-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (map)
  [env resolver-props]
  (io/read-edn-file handler.config/WEBSITE-CONTENT-FILEPATH))

(defresolver get-content
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (namespaced map)
             ;  {:website-content.editor/get-content (map)}
             [env resolver-props]
             {:website-content.editor/get-content (get-content-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-content])

(pathom/reg-handlers! ::handlers HANDLERS)
